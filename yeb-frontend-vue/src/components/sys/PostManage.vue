<template>
  <div>
    <el-input
      size="small"
      class="addPostInput"
      placeholder="添加职位..."
      prefix-icon="el-icon-plus"
      v-model="post.name"
      @keydown.enter.native="addPosition"
    >
    </el-input>
    <el-button
      type="primary"
      icon="el-icon-plus"
      size="small"
      @click="addPosition"
      >添加</el-button
    >
    <el-button
      size="small"
      type="danger"
      style="margin-top: 8px"
      icon="el-icon-delete"
      :disabled="this.multipleSelection.length == 0"
      @click="deleteBatch"
      >批量删除</el-button
    >
    <el-table
      @selection-change="handleSelectChange"
      :data="tableData"
      :header-cell-style="{ textAlign: 'center' }"
      :cell-style="{ textAlign: 'center' }"
      size="mini"
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
      <el-table-column prop="name" label="职位" width="180"> </el-table-column>
      <el-table-column prop="createDate" label="创建时间" width="180" sortable>
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
    <!-- 编辑职位信息弹框 -->
    <el-dialog
      title="编辑职位"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <div>
        <el-tag>职位名称</el-tag>
        <el-input
          v-model="updatePost.name"
          size="small"
          class="updatePostInput"
        ></el-input>
        <br />
        <el-tag>是否启用</el-tag>
        <el-switch
          style="margin-left: 10px"
          v-model="updatePost.enabled"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-text="已启用"
          inactive-text="已禁用"
        ></el-switch>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatePosition">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { postList, addPost, delPost, editPost, delBatch } from "@/api/sys/post";
export default {
  name: "PostManage",
  data() {
    return {
      post: {
        name: "",
      },
      updatePost: {
        name: "",
        enabled: "",
      },
      tableData: [],
      dialogVisible: false,
      multipleSelection: [],
    };
  },
  mounted() {
    this.initPositions();
  },
  methods: {
    // 获取职位信息列表
    initPositions() {
      postList().then((resp) => {
        if (resp) {
          this.tableData = resp.data;
        }
      });
    },
    // 添加
    addPosition() {
      if (this.post.name) {
        addPost(this.post).then((resp) => {
          if (resp) {
            this.post.name = "";
            this.$message.success(resp.message);
            this.initPositions();
          }
        });
      } else {
        this.$message.error("职位名称不能为空哦！");
      }
    },
    // 更新职位信息
    updatePosition() {
      editPost(this.updatePost).then((resp) => {
        if (resp) {
          this.dialogVisible = false;
          this.$message.success(resp.message);
          this.initPositions();
        }
      });
    },
    // 编辑
    handleEdit(index, data) {
      Object.assign(this.updatePost, data);
      this.updatePost.createDate = "";
      this.dialogVisible = true;
    },
    // 删除
    handleDelete(index, data) {
      this.$confirm(
        "此操作将永久删除【" + data.name + "】职位, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          delPost(data.id).then((resp) => {
            if (resp) {
              this.$message.success(resp.message);
              this.initPositions();
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
          "】条职位信息, 是否继续?",
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
              this.initPositions();
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

<style scoped>
.addPostInput {
  width: 300px;
  margin: 0 10px 16px 0;
}

.updatePostInput {
  margin-left: 10px;
  width: 240px;
}
</style>