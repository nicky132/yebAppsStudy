<template>
  <div class="login">
    <el-form
      v-loading="loading"
      element-loading-text="登录中..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      ref="form"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
    >
      <h3 class="login-title">系统登录</h3>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          prefix-icon="el-icon-user"
          placeholder="请输入用户名"
          type="text"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          prefix-icon="el-icon-lock"
          placeholder="请输入密码"
          type="password"
          clearable
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          v-model="loginForm.code"
          class="login-code"
          prefix-icon="el-icon-key"
          placeholder="验证码"
          type="text"
          clearable
        ></el-input>
        <img :src="codeUrl" @click="getCode" style="float: right" />
      </el-form-item>
      <el-checkbox v-model="rememberMe" class="login-remember"
        >记住我</el-checkbox
      >
      <el-button type="primary" style="width: 100%" @click="loginHandle"
        >登录</el-button
      >
    </el-form>
  </div>
</template>

<script>
import { login } from "@/api/sys/login";
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: "admin",
        password: "123456",
        code: "",
      },
      loading: false,
      codeUrl: "/yeb/captcha?time=" + new Date(),
      rememberMe: true,
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的用户名" },
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
        ],
        code: [{ required: true, trigger: "blur", message: "请输入验证码" }],
      },
    };
  },
  methods: {
    getCode() {
      this.codeUrl = "/yeb/captcha?time=" + new Date();
    },
    loginHandle() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true;
          login(this.loginForm).then((resp) => {
            if (resp) {
              this.loading = false;
              // 用户token存入sessionStorage
              const tokenStr = resp.data.tokenHead + resp.data.token;
              window.sessionStorage.setItem("tokenStr", tokenStr);
              // 跳转首页
              this.$router.replace({ path: "/home" });
              this.$message.success("欢迎回来，" + this.loginForm.username);
            } else {
              this.loading = false;
              this.$message.error(resp.message);
            }
          });
        } else {
          this.$message.error("请填写所有字段！");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.login {
  width: 100%;
  height: 100%;
  min-width: 1000px;
  background-size: 100% 100%;
  background-position: center center;
  overflow: auto;
  background-repeat: no-repeat;
  position: fixed;
  line-height: 100%;
  background-image: url("../assets/login-background2.jpg");
}

.login-form {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background-color: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
}

.login-code {
  width: 250px;
  margin-right: 5px;
}

.login-remember {
  text-align: left;
  margin: 0 0 15px 0;
}
</style>