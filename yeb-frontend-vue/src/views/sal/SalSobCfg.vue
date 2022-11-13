<template>
  <div>
    <el-table
      :data="emps"
      :header-cell-style="{ textAlign: 'center' }"
      :cell-style="{ textAlign: 'center' }"
      v-loading="loading"
      element-loading-text="拼命加载中..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgb(0, 0, 0, 0.8)"
      border
      stripe
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column prop="name" label="姓名" fixed width="120">
      </el-table-column>
      <el-table-column prop="workID" label="工号" width="100">
      </el-table-column>
      <el-table-column prop="email" label="邮箱地址" width="160">
      </el-table-column>
      <el-table-column prop="phone" label="电话号码" width="120">
      </el-table-column>
      <el-table-column prop="department.name" label="所属部门" width="120">
      </el-table-column>
      <el-table-column prop="salary" label="工资账套" width="200">
        <template slot-scope="scope">
          <el-tooltip placement="right" v-if="scope.row.salary">
            <div slot="content">
              <table>
                <tr>
                  <td>基本工资</td>
                  <td>{{ scope.row.salary.basicSalary }}</td>
                </tr>
                <tr>
                  <td>交通补助</td>
                  <td>{{ scope.row.salary.trafficSalary }}</td>
                </tr>
                <tr>
                  <td>午餐补助</td>
                  <td>{{ scope.row.salary.lunchSalary }}</td>
                </tr>
                <tr>
                  <td>奖金</td>
                  <td>{{ scope.row.salary.bonus }}</td>
                </tr>
                <tr>
                  <td>养老金比率</td>
                  <td>{{ scope.row.salary.pensionPer }}</td>
                </tr>
                <tr>
                  <td>养老金基础</td>
                  <td>{{ scope.row.salary.pensionBase }}</td>
                </tr>
                <tr>
                  <td>医疗保险比率</td>
                  <td>{{ scope.row.salary.medicalPer }}</td>
                </tr>
                <tr>
                  <td>医疗保险基数</td>
                  <td>{{ scope.row.salary.medicalBase }}</td>
                </tr>
                <tr>
                  <td>公积金比率</td>
                  <td>{{ scope.row.salary.accumulationFundPer }}</td>
                </tr>
                <tr>
                  <td>公积金基数</td>
                  <td>{{ scope.row.salary.accumulationFundBase }}</td>
                </tr>
              </table>
            </div>
            <el-tag>{{ scope.row.salary.name }}</el-tag>
          </el-tooltip>
          <el-tag v-else>暂未设置</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popover
            placement="left"
            title="编辑工资账套"
            width="200"
            trigger="click"
            @show="showPop(scope.row.salary)"
            @hide="hidePop(scope.row)"
          >
            <div>
              <el-select v-model="currentSalary" placeholder="请选择">
                <el-option
                  v-for="item in salaries"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </div>
            <el-button slot="reference" size="small" type="danger"
              >修改工资账套</el-button
            >
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <div style="display: flex; justify-content: flex-end">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30]"
        :total="total"
        background
        layout="total, sizes, prev, pager, next, jumper"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { empsalList, salList, editEmpSal } from "@/api/sal/empsal";
export default {
  name: "SalSobCfg",
  data() {
    return {
      emps: [],
      salaries: [],
      currentSalary: null,
      loading: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
    };
  },
  mounted() {
    this.initEmps();
    this.initSalaries();
  },
  methods: {
    hidePop(data) {
      if (this.currentSalary && this.currentSalary != data.salary.id) {
        editEmpSal(data.id, this.currentSalary).then((resp) => {
          if (resp) {
            this.initEmps();
            this.$message.success(resp.message);
          }
        });
      }
    },
    showPop(data) {
      if (data) {
        this.currentSalary = data.id;
      } else {
        this.currentSalary = null;
      }
    },
    initSalaries() {
      salList().then((resp) => {
        if (resp) {
          this.salaries = resp.data;
        }
      });
    },
    initEmps() {
      this.loading = true;
      empsalList(this.currentPage, this.pageSize).then((res) => {
        if (res) {
          this.loading = false;
          this.emps = res.data.data;
          this.total = res.data.total;
        }
      });
    },
    handleSizeChange(size) {
      this.loading = true;
      this.pageSize = size;
      empsalList(this.currentPage, this.pageSize).then((resp) => {
        if (resp) {
          this.loading = false;
          this.emps = resp.data.data;
        }
      });
    },
    handleCurrentChange(currentPage) {
      this.loading = true;
      this.currentPage = currentPage;
      empsalList(this.currentPage, this.pageSize).then((resp) => {
        if (resp) {
          this.loading = false;
          this.emps = resp.data.data;
        }
      });
    },
  },
};
</script>

<style scoped>
</style>