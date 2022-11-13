<template>
  <div>
    <el-card class="box-card" style="width: 400px">
      <div slot="header" class="clearfix">
        <span>{{ admin.name }}</span>
      </div>
      <div>
        <div style="display: flex; justify-content: center">
          <el-upload
            action="/yeb/admin/useface"
            :headers="header"
            :data="admin"
            :show-file-list="false"
            :on-success="onSuccess"
          >
            <img
              :src="admin.userFace"
              title="点击修改用户头像"
              alt=""
              style="height: 100px; width: 100px; border-radius: 50px"
            />
          </el-upload>
        </div>
        <div>
          电话号码：<el-tag>{{ admin.telephone }}</el-tag>
        </div>
        <div>
          手机号码：<el-tag>{{ admin.phone }}</el-tag>
        </div>
        <div>
          居住地址：<el-tag>{{ admin.address }}</el-tag>
        </div>
        <div>
          用户标签：<el-tag
            type="success"
            v-for="(role, index) in admin.roles"
            :key="index"
            >{{ role.nameZh }}</el-tag
          >
        </div>
        <div
          style="display: flex; justify-content: space-around; margin-top: 8px"
        >
          <el-button type="primary" size="mini" @click="showUpdateInfoView"
            >修改信息</el-button
          >
          <el-button type="danger" size="mini" @click="showUpdatePwdView"
            >修改密码</el-button
          >
        </div>
      </div>
    </el-card>
    <el-dialog
      title="编辑用户信息"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="35%"
    >
      <el-form :model="adminForm" :rules="rules" ref="form" label-width="80px">
        <el-form-item label="用户昵称" prop="name">
          <el-input v-model="adminForm.name"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="telephone">
          <el-input v-model="adminForm.telephone"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="adminForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="居住地址" prop="address">
          <el-input v-model="adminForm.address"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateAdminInfo">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="更新用户密码"
      :visible.sync="pwdDialogVisible"
      :before-close="handleClose"
      width="35%"
    >
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="请输入旧密码" prop="oldPass">
          <el-input
            type="password"
            v-model="ruleForm.oldPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="请输入新密码" prop="pass">
          <el-input
            type="password"
            v-model="ruleForm.pass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >提交</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getInfo, logout } from "@/api/sys/login";
import { updateInfo, updateUserPwd } from "@/api/sys/admininfo";
export default {
  name: "AdminInfo",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      admin: {
        name: "",
        telephone: "",
        phone: "",
        address: "",
      },
      adminForm: {
        name: "",
        telephone: "",
        phone: "",
        address: "",
      },
      ruleForm: {
        oldPass: "",
        pass: "",
        checkPass: "",
      },
      dialogVisible: false,
      pwdDialogVisible: false,
      header: {
        Authorization: window.sessionStorage.getItem("tokenStr"),
      },
      rules: {
        name: [{ required: true, message: "请输入用户昵称", trigger: "blur" }],
        telephone: [
          { required: true, message: "请输入电话号码", trigger: "blur" },
        ],
        phone: [{ required: true, message: "请输入手机号码", trigger: "blur" }],
        address: [
          { required: true, message: "请输入居住地址", trigger: "blur" },
        ],
        oldPass: [{ validator: validatePass, trigger: "blur" }],
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.initAdminInfo();
  },
  methods: {
    initAdminInfo() {
      getInfo().then((resp) => {
        if (resp) {
          this.admin = resp;
          this.adminForm = Object.assign({}, this.admin);
          window.sessionStorage.setItem("user", JSON.stringify(resp));
          this.$store.commit("initAdmin", resp);
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
    showUpdateInfoView() {
      this.dialogVisible = true;
      this.adminForm = Object.assign({}, this.admin);
    },
    updateAdminInfo() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          updateInfo(this.adminForm).then((resp) => {
            if (resp) {
              this.initAdminInfo();
              this.dialogVisible = false;
              this.$message.success(resp.message);
            }
          });
        } else {
          this.$message.error("请填写所有字段！");
          return false;
        }
      });
    },
    showUpdatePwdView() {
      this.pwdDialogVisible = true;
      this.ruleForm = {};
    },
    onSuccess() {
      this.initAdminInfo();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.adminId = this.admin.id;
          updateUserPwd(this.ruleForm).then((resp) => {
            if (resp) {
              // 密码更新成功后退出登录并清除sessionStorage中的用户信息
              logout();
              window.sessionStorage.removeItem("tokenStr");
              window.sessionStorage.removeItem("user");
              this.$store.commit("initRoutes", []);
              this.$router.replace("/");
              this.$message.success(resp.message);
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style scopd>
</style>