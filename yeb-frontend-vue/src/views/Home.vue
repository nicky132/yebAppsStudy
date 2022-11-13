<template>
  <el-container>
    <el-header class="homeHeader">
      <div class="title">云E办</div>
      <div>
        <el-button
          icon="el-icon-bell"
          type="text"
          size="normal"
          style="color: black; margin-right: 8px"
          @click="onlineChat"
          title="在线聊天"
        ></el-button>
        <el-dropdown class="userInfo" @command="commandHandler">
          <div class="el-dropdown-link">
            {{ user.name }}<img :src="user.userFace" />
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="userInfo">个人中心</el-dropdown-item>
            <el-dropdown-item command="logout">注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside class="homeSide" width="200px">
        <el-menu router>
          <el-submenu
            :index="index + ''"
            v-for="(item, index) in routes"
            :key="index"
            v-if="!item.hidden"
          >
            <template slot="title">
              <i :class="item.iconCls" style="color: #238cd1"></i>
              <span>&nbsp;&nbsp;{{ item.name }}</span>
            </template>
            <el-menu-item-group>
              <el-menu-item
                :index="children.path"
                v-for="(children, index) in item.children"
                :key="index"
              >
                {{ children.name }}
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <!-- 主体内容 -->
        <el-breadcrumb
          v-if="this.$route.path !== '/home'"
          separator-class="el-icon-arrow-right"
        >
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{ this.$route.name }}</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="homeWelcome" v-if="this.$route.path == '/home'">
          欢迎来到云e办系统！
        </div>
        <router-view style="margin-top: 16px" />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { logout } from "@/api/sys/login";
import { resetRouter } from "@/router";
export default {
  name: "Home",
  data() {
    return {
      // user: JSON.parse(window.sessionStorage.getItem("user")),
    };
  },
  methods: {
    // 在线聊天
    onlineChat() {
      this.$router.push({ path: "/chat" });
    },
    commandHandler(command) {
      if (command === "logout") {
        this.$confirm("此操作将注销登录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            // 注销登录
            logout().then((resp) => {
              if (resp) {
                // 清空用户信息
                window.sessionStorage.removeItem("user");
                window.sessionStorage.removeItem("tokenStr");
                // 清空菜单
                this.$store.commit("initRoutes", []);
                resetRouter();
                // 跳转登录页面
                this.$router.replace({ path: "/" });
                this.$message.success("注销成功！");
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消操作！",
            });
          });
      }
      if (command == "userInfo") {
        this.$router.push("/admininfo");
      }
    },
  },
  computed: {
    routes() {
      return this.$store.state.routes;
    },
    user() {
      return this.$store.state.currentAdmin;
    },
  },
};
</script>

<style scoped>
.homeHeader {
  text-align: center;
  background: #299be7;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.title {
  color: white;
  font-size: 30px;
  font-family: 楷体;
}

.homeSide {
  overflow: inherit;
}

.userInfo {
  cursor: pointer;
}

.el-dropdown-link img {
  width: 48px;
  height: 48px;
  border-radius: 24px;
  margin: 0 0 -13px 10px;
}

.homeWelcome {
  text-align: center;
  font-size: 30px;
  font-family: 华文楷体;
  color: #409eff;
  padding-top: 100px;
}
</style>