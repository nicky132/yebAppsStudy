<template>
  <div>
    <div class="permissTool">
      <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input
        size="small"
        placeholder="请输入角色中文名"
        v-model="role.nameZh"
        @keydown.enter.native="doAddRole"
      >
      </el-input>
      <el-button
        size="small"
        type="primary"
        icon="el-icon-plus"
        @click="doAddRole"
        >添加角色</el-button
      >
    </div>
    <div class="permissMain">
      <el-collapse accordion v-model="activeName" @change="change">
        <el-collapse-item
          v-for="(role, index) in roles"
          :key="index"
          :title="role.nameZh"
          :name="role.id"
        >
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问资源</span>
              <el-button
                style="float: right; padding: 3px 0; color: #ff0000"
                type="text"
                icon="el-icon-delete"
                @click="doDelRole(role.id)"
                >删除角色</el-button
              >
            </div>
            <div>
              <el-tree
                :data="allMenus"
                :props="defaultProps"
                :default-checked-keys="selectedMenus"
                show-checkbox
                ref="tree"
                node-key="id"
              >
              </el-tree>
              <div style="display: flex; justify-content: flex-end">
                <el-button size="mini" @click="cancel()">取消修改</el-button>
                <el-button
                  size="mini"
                  type="primary"
                  @click="doUpdate(role.id, index)"
                  >确认修改</el-button
                >
              </div>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
import {
  roleList,
  allMenus,
  menusWithRole,
  updateRoles,
  addRole,
  delRole,
} from "@/api/sys/permiss";
export default {
  name: "PermissManage",
  data() {
    return {
      role: {
        name: "",
        nameZh: "",
      },
      roles: [],
      allMenus: [],
      selectedMenus: [],
      activeName: -1,
      defaultProps: {
        children: "children",
        label: "name",
      },
    };
  },
  mounted() {
    this.initRoles();
  },
  methods: {
    initRoles() {
      roleList().then((resp) => {
        if (resp) {
          this.roles = resp.data;
        }
      });
    },
    change(rid) {
      if (rid) {
        this.initMenus();
        this.initSelectedMenus(rid);
      }
    },
    initMenus() {
      allMenus().then((resp) => {
        if (resp) {
          this.allMenus = resp.data;
        }
      });
    },
    initSelectedMenus(rid) {
      menusWithRole(rid).then((resp) => {
        this.selectedMenus = resp.data;
      });
    },
    // 添加角色
    doAddRole() {
      if (this.role.name && this.role.nameZh) {
        addRole(this.role).then((res) => {
          if (res) {
            this.initRoles();
            this.role.name = "";
            this.role.nameZh = "";
            this.$message.success(res.message);
          }
        });
      } else {
        this.$message.error("字段不能为空哦！");
      }
    },
    // 删除角色
    doDelRole(rid) {
      this.$confirm("你是否准备删除该角色, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          delRole(rid).then((res) => {
            if (res) {
              this.activeName = -1;
              this.initRoles();
              this.$message.success(res.message);
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 取消修改
    cancel() {
      this.activeName = -1;
    },
    // 确认修改
    doUpdate(rid, index) {
      let tree = this.$refs.tree[index];
      let mids = tree.getCheckedKeys(true);
      mids.forEach((key) => {
        rid += "&menuIds=" + key;
      });
      console.log(rid);
      updateRoles(rid).then((resp) => {
        if (resp) {
          this.initRoles();
          this.activeName = -1;
          this.$message.success(resp.message);
        }
      });
    },
  },
};
</script>

<style scoped>
.permissTool {
  display: flex;
  justify-content: flex-start;
}

.permissTool .el-input {
  width: 300px;
  margin-right: 6px;
}

.permissMain {
  margin-top: 10px;
  width: 700px;
}
</style>