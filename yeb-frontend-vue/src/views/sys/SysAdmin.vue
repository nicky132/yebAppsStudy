<template>
  <div>
    <div style="margin-top: 10px; display: flex; justify-content: center">
      <el-input
        placeholder="通过用户名搜索用户.."
        prefix-icon="el-icon-search"
        style="width: 400px"
        size="small"
        v-model="keyword"
      ></el-input>
      <el-button
        icon="el-icon-search"
        size="small"
        style="margin-left: 8px"
        type="primary"
        @click="doSearch"
        >搜索</el-button
      >
    </div>
    <div class="admincontent">
      <el-card class="box-card" v-for="(admin, index) in admins" :key="index">
        <div slot="header" class="clearfix">
          <span>{{ admin.name }}</span>
          <el-button
            style="float: right; padding: 3px 0; color: #ff0000"
            icon="el-icon-delete"
            type="text"
            @click="deleteAdmin(admin)"
          ></el-button>
        </div>
        <div
          style="display: flex; justify-content: center; margin-bottom: 10px"
        >
          <div>
            <img :src="admin.userFace" class="userface" />
          </div>
        </div>
        <div class="userinfo-content">
          <div>用户名：{{ admin.name }}</div>
          <div>手机号码：{{ admin.phone }}</div>
          <div>电话号码：{{ admin.telephone }}</div>
          <div>地址：{{ admin.address }}</div>
          <div>
            用户状态：
            <el-switch
              @change="doChange(admin)"
              v-model="admin.enabled"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用"
            >
            </el-switch>
          </div>
          <div>
            用户角色:
            <el-tag
              type="success"
              v-for="(item, index) in admin.roles"
              :key="index"
              size="small"
              style="margin-right: 4px; margin-bottom: 4px"
              >{{ item.nameZh }}</el-tag
            >
            <el-popover
              placement="bottom"
              title="角色列表"
              @show="showPop(admin)"
              @hide="hidePop(admin)"
              trigger="click"
              content=""
            >
              <el-select v-model="selectRoles" multiple placeholder="请选择">
                <el-option
                  v-for="(item, index) in allRoles"
                  :key="index"
                  :label="item.nameZh"
                  :value="item.id"
                >
                </el-option>
              </el-select>
              <el-button
                type="text"
                icon="el-icon-more"
                slot="reference"
              ></el-button>
            </el-popover>
          </div>
          <div>备注:{{ admin.remark }}</div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import {
  adminList,
  editAdmin,
  delAdmin,
  updateAdminRole,
} from "@/api/sys/admin";
import { roleList } from "@/api/sys/permiss";
export default {
  name: "SysAdmin",
  data() {
    return {
      admins: [],
      keyword: "",
      allRoles: [],
      selectRoles: [],
    };
  },
  mounted() {
    this.initAdmins();
  },
  methods: {
    initAdmins() {
      adminList().then((res) => {
        if (res) {
          this.admins = res;
        }
      });
    },
    doSearch() {
      adminList(this.keyword).then((res) => {
        this.keyword = "";
        this.admins = res;
      });
    },
    deleteAdmin(admin) {
      this.$confirm(
        "你是否准备删除【" + admin.name + "】操作员, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          delAdmin(admin.id).then((res) => {
            if (res) {
              this.initAdmins();
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
    doChange(admin) {
      editAdmin(admin).then((res) => {
        if (res) {
          this.initAdmins();
          this.$message.success(res.message);
        }
      });
    },
    initAllRoles() {
      roleList().then((res) => {
        if (res) {
          this.allRoles = res.data;
        }
      });
    },
    showPop(admin) {
      this.initAllRoles();
      let roles = admin.roles;
      this.selectRoles = [];
      roles.forEach((role) => {
        this.selectRoles.push(role.id);
      });
    },
    hidePop(admin) {
      let rolos = [];
      let flag = false;
      Object.assign(rolos, admin.roles);
      if (rolos.length !== this.selectRoles.length) {
        flag = true;
      } else {
        for (let i = 0; i < rolos.length; i++) {
          let role = rolos[i];
          for (let j = 0; j < this.selectRoles.length; j++) {
            let sr = this.selectRoles[j];
            if (role.id == sr) {
              rolos.splice(i, 1);
              i--;
              break;
            }
          }
        }
        if (rolos.length !== 0) {
          flag = true;
        }
      }
      if (flag) {
        let data = admin.id;
        this.selectRoles.forEach((role) => {
          data += "&rids=" + role;
        });
        updateAdminRole(data).then((res) => {
          if (res) {
            this.initAdmins();
            this.$message.success(res.message);
          }
        });
      }
    },
  },
};
</script>

<style scoped>
.admincontent {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
.box-card {
  margin-top: 10px;
  width: 340px;
}
.userface {
  width: 67px;
  border-radius: 50%;
}
.userinfo-content {
  font-size: 12px;
  color: #409eff;
}
</style>