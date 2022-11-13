<template>
  <div>
    <div>
      <el-input
        size="small"
        v-model="jl.name"
        placeholder="添加职称名字..."
        prefix-icon="el-icon-plus"
        style="width: 300px"
        @keydown.enter.native="addJoblevel"
      ></el-input>
      <el-select
        size="small"
        v-model="jl.titleLevel"
        placeholder="请选择职称等级"
        style="margin-left: 8px; margin-right: 6px"
      >
        <el-option
          v-for="item in titlelevels"
          :key="item"
          :value="item"
          :label="item"
        >
        </el-option>
      </el-select>
      <el-button
        size="small"
        type="primary"
        icon="el-icon-plus"
        @click="addJoblevel"
        >添加</el-button
      >
      <el-button
        size="small"
        type="danger"
        icon="el-icon-delete"
        style="margin-top: 8px"
        :disabled="this.multipleSelection.length == 0"
        @click="deleteBatch"
        >批量删除</el-button
      >
    </div>
    <div style="margin-top: 16px">
      <el-table
        @selection-change="handleSelectChange"
        :data="tableData"
        :header-cell-style="{ textAlign: 'center' }"
        :cell-style="{ textAlign: 'center' }"
        size="small"
        stripe
        border
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column
          prop="id"
          label="编号"
          width="100"
          sortable
          :cell-style="{ textAlign: 'center' }"
        >
        </el-table-column>
        <el-table-column prop="name" label="职称名称" width="180">
        </el-table-column>
        <el-table-column prop="titleLevel" label="职称等级" width="180">
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="创建时间"
          width="180"
          sortable
        >
        </el-table-column>
        <el-table-column prop="enabled" label="是否启用" width="180">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.enabled" type="success">已启用</el-tag>
            <el-tag v-else type="danger">已禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 编辑职称信息弹框 -->
      <el-dialog
        title="编辑职称"
        width="30%"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <div>
          <el-tag>职称名称</el-tag>
          <el-input
            v-model="updateJL.name"
            size="small"
            class="updatePostInput"
            style="margin-left: 10px; width: 240px"
          ></el-input
          ><br />
          <el-tag>职称等级</el-tag>
          <el-select
            size="small"
            v-model="updateJL.titleLevel"
            placeholder="请选择职称等级"
            style="margin-left: 10px"
          >
            <el-option
              v-for="item in titlelevels"
              :key="item"
              :value="item"
              :label="item"
            >
            </el-option>
          </el-select>
          <br />
          <el-tag>是否启用</el-tag>
          <el-switch
            style="margin-left: 10px"
            v-model="updateJL.enabled"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="已启用"
            inactive-text="已禁用"
          ></el-switch>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateJobLevel">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  jobLevelList,
  addJobLevel,
  delJobLevel,
  editJobLevel,
  delBatch,
} from "@/api/sys/joblevel";
export default {
  name: "JobLevelManage",
  data() {
    return {
      jl: {
        name: "",
        titleLevel: "",
      },
      updateJL: {
        name: "",
        titleLevel: "",
        enabled: "",
      },
      titlelevels: ["正高级", "副高级", "初级", "中级", "高级"],
      tableData: [],
      dialogVisible: false,
      multipleSelection: [],
    };
  },
  mounted() {
    this.initJobLevels();
  },
  methods: {
    // 获取职称信息列表
    initJobLevels() {
      jobLevelList().then((resp) => {
        if (resp) {
          this.tableData = resp.data;
        }
      });
    },
    // 添加职称信息
    addJoblevel() {
      if (this.jl.name && this.jl.titleLevel) {
        addJobLevel(this.jl).then((resp) => {
          if (resp) {
            this.jl.name = "";
            this.jl.titleLevel = "";
            this.$message.success(resp.message);
            this.initJobLevels();
          }
        });
      } else {
        this.$message.error("字段不能为空哦！");
      }
    },
    // 更新职位信息
    updateJobLevel() {
      editJobLevel(this.updateJL).then((resp) => {
        if (resp) {
          this.dialogVisible = false;
          this.$message.success(resp.message);
          this.initJobLevels();
        }
      });
    },
    // 编辑
    handleEdit(index, data) {
      Object.assign(this.updateJL, data);
      this.updateJL.createDate = "";
      this.dialogVisible = true;
    },
    // 删除
    handleDelete(index, data) {
      this.$confirm(
        "此操作将永久删除【" + data.name + "】职称, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          delJobLevel(data.id).then((resp) => {
            if (resp) {
              this.$message.success(resp.message);
              this.initJobLevels();
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
    // 批量删除
    deleteBatch() {
      this.$confirm(
        "此操作将永久删除【" +
          this.multipleSelection.length +
          "】条职称信息, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          let ids = "?";
          this.multipleSelection.forEach((item) => {
            ids += "ids=" + item.id + "&";
          });
          delBatch(ids).then((resp) => {
            if (resp) {
              this.$message.success(resp.message);
              this.initJobLevels();
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
    handleSelectChange(val) {
      this.multipleSelection = val;
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

<style>
</style>