<template>
  <div>
    <div style="margin: 8px 0 10px 0; display: flex; justify-content: space-between">
      <div>
        <el-input
          style="width: 300px; margin-right: 10px"
          prefix-icon="el-icon-search"
          placeholder="请输入员工名进行搜索..."
          v-model="empName"
          size="small"
          clearable
          @clear="initEmps"
          @keydown.enter.native="initEmps"
        ></el-input>
        <el-button type="primary" size="small" icon="el-icon-search" @click="initEmps"
          >搜索</el-button
        >
        <el-button type="primary" size="small">
          <i class="fa fa-angle-double-down" />高级搜索</el-button
        >
      </div>
      <div>
          <el-button
            type="success" size="small">
            <i class="fa fa-level-up" aria-hidden="true"></i>
            导入数据</el-button
          >
        </el-upload>
        <el-button type="success" size="small">
          <i class="fa fa-level-down" aria-hidden="true"></i>
          导出数据</el-button
        >
        <el-button type="primary" size="small" icon="el-icon-plus" @click="showAddView">添加员工</el-button>
      </div>
    </div>
    <div>
      <el-table
          :data="emps"
          :header-cell-style="{ textAlign: 'center' }"
          :cell-style="{ textAlign: 'center' }"
          stripe
          border
          size="small"
          v-loading="loading"
          element-loading-text="拼命加载中..."
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgb(0, 0, 0, 0.8)"
          style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            fixed="left"
            width="70">
        </el-table-column>
        <el-table-column
            prop="workID"
            label="工号"
            width="90">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别"
            width="60">
        </el-table-column>
        <el-table-column
            prop="birthday"
            label="出生日期"
            width="100">
        </el-table-column>
        <el-table-column
            prop="idCard"
            label="身份证号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            label="婚姻状况"
            width="100">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            label="民族"
            width="50">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            label="籍贯"
            width="90">
        </el-table-column>
        <el-table-column
            prop="politicsStatus.name"
            label="政治面貌"
            width="110">
        </el-table-column>
        <el-table-column
            prop="email"
            label="电子邮件"
            width="160">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话号码"
            width="120">
        </el-table-column>
        <el-table-column
            prop="address"
            label="联系地址"
            width="220">
        </el-table-column>
        <el-table-column
            prop="department.name"
            label="所属部门"
            width="100">
        </el-table-column>
        <el-table-column
            prop="joblevel.name"
            label="职称"
            width="110">
        </el-table-column>
        <el-table-column
            prop="position.name"
            label="职位"
            width="90">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            label="聘用形式"
            width="100">
        </el-table-column>
        <el-table-column
            prop="tiptopDegree"
            label="最高学历"
            width="80">
        </el-table-column>
        <el-table-column
            prop="school"
            label="学校"
            width="100">
        </el-table-column>
        <el-table-column
            prop="specialty"
            label="专业"
            width="100">
        </el-table-column>
        <el-table-column
            prop="workState"
            label="在职状态"
            width="90">
        </el-table-column>
        <el-table-column
            prop="beginDate"
            label="入职日期"
            width="100">
        </el-table-column>
        <el-table-column
            prop="conversionTime"
            label="转正日期"
            width="100">
        </el-table-column>
        <el-table-column
            prop="beginContract"
            label="合同起始日期"
            width="110">
        </el-table-column>
        <el-table-column
            prop="endContract"
            label="合同结束日期"
            width="110">
        </el-table-column>
        <el-table-column
            label="合同期限"
            width="100">
          <template slot-scope="scope">
            <el-tag>{{scope.row.contractTerm}}年</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            label="操作"
            fixed="right"
            width="300">
          <template slot-scope="scope">
            <el-button size="mini">编辑</el-button>
            <el-button size="mini" type="primary">查看高级资料</el-button>
            <el-button size="mini" type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="display:flex; justify-content: flex-end">
        <el-pagination 
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30]"
          :total="total"
          background
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </div>
    </div>
    <el-dialog
      title="添加员工"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose"
      >
        <div>
          <el-row >
            <el-col :span="6"></el-col>
            <el-col :span="6"></el-col>
            <el-col :span="6"></el-col>
            <el-col :span="6"></el-col>
          </el-row>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addEmp">确 定</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import { empList } from "@/api/sys/emp";
export default {
  name: "EmpBasic",
  data() {
    return {
      emps: [],
      loading: false,
      dialogVisible: false,
      total: 0,
      currentPage: 1,
      pageSize: 10,
      empName: "",
      emp: {
        name: "",
        gender: "",
        birthday: "",
        idCard: "",
        wedlock: "",
        nationId: null,
        nativePlace: "",
        politicId: null,
        email: "",
        phone: "",
        address: "",
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: "",
        tiptopDegree: "",
        specialty: "",
        school: "",
        beginDate: "",
        workState: "",
        workID: "",
        contractTerm: null,
        conversionTime: "",
        notWorkDate: null,
        beginContract: "",
        endContract: "",
        workAge: null,
        salaryId: null,
      },
      headers: {
        Authorization: window.sessionStorage.getItem("tokenStr"),
      },
    };
  },
  mounted() {
    this.initEmps();
  },
  methods: {
    initEmps() {
      this.loading = true;
      empList(this.currentPage, this.pageSize, this.empName).then((resp) => {
        if (resp) {
          this.loading = false;
          this.emps = resp.data.data;
          this.total = resp.data.total;
        }
      });
    },
    showAddView() {
      this.dialogVisible = true;
    },
    addEmp() {},
    handleSizeChange(size) {
      this.loading = true;
      this.pageSize = size;
      empList(this.currentPage, this.pageSize).then((resp) => {
        if (resp) {
          this.loading = false;
          this.emps = resp.data.data;
        }
      });
    },
    handleCurrentChange(currentPage) {
      this.loading = true;
      this.currentPage = currentPage;
      empList(this.currentPage, this.pageSize).then((resp) => {
        if (resp) {
          this.loading = false;
          this.emps = resp.data.data;
        }
      });
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