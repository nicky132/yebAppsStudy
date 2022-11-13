<template>
  <div style="width: 500px">
    <el-input
      placeholder="输入部门关键字进行搜索..."
      prefix-icon="el-icon-search"
      v-model="filterText"
    >
    </el-input>
    <el-tree
      style="margin-top: 8px"
      :data="deps"
      :props="defaultProps"
      :filter-node-method="filterNode"
      :expand-on-click-node="false"
      class="filter-tree"
      ref="tree"
    >
      <span
        class="custom-tree-node"
        slot-scope="{ node, data }"
        style="display: flex; justify-content: space-between; width: 100%"
      >
        <span>{{ data.name }}</span>
        <span>
          <el-button
            type="primary"
            size="mini"
            style="padding: 5px"
            @click="() => showAddDep(data)"
          >
            添加部门
          </el-button>
          <el-button
            type="danger"
            size="mini"
            style="padding: 5px"
            @click="() => deleteDep(data)"
          >
            删除部门
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog
      title="添加部门"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <div>
        <el-tag>上级部门</el-tag>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ pname }}
      </div>
      <div style="margin-top: 5px; display: flex; align-items: center">
        <el-tag>部门名称</el-tag
        ><el-input
          style="margin-left: 5px; width: 60%"
          size="small"
          placeholder="请输入部门名称"
          v-model="dep.name"
        ></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doAddDep">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { depList, addDep, delDep } from "@/api/sys/dep";
export default {
  name: "DepManage",
  data() {
    return {
      deps: [],
      filterText: "",
      defaultProps: {
        children: "children",
        label: "name",
      },
      dialogVisible: false,
      dep: {
        name: "",
        parentId: -1,
      },
      pname: "",
    };
  },
  mounted() {
    this.initDeps();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    // 部门信息
    initDeps() {
      depList().then((resp) => {
        if (resp) {
          this.deps = resp.data;
        }
      });
    },
    initDep() {
      this.dep = {
        name: "",
        parentId: -1,
      };
      this.pname = "";
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    showAddDep(data) {
      this.dep.parentId = data.id;
      this.pname = data.name;
      this.dialogVisible = true;
    },
    doAddDep() {
      addDep(this.dep).then((resp) => {
        if (resp) {
          this.initDeps();
          this.dialogVisible = false;
          this.initDep();
          this.$message.success(resp.message);
        }
      });
    },
    deleteDep(data) {
      if (data.isParent) {
        this.$message.error("父部门删除失败！");
      } else {
        this.$confirm(
          "你是否准备删除【" + data.name + "】部门，是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            delDep(+data.id).then((res) => {
              if (res) {
                this.initDeps();
                this.dialogVisible = false;
                this.initDep();
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
      }
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
  },
};
</script>

<style scoped>
</style>