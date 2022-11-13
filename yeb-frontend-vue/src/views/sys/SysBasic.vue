<template>
  <div>
    <el-tabs v-model:active-name="activeName" type="card">
      <el-tab-pane label="部门管理" name="dep-manage">
        <dep-manage />
      </el-tab-pane>
      <el-tab-pane label="职位管理" name="post-manage">
        <post-manage />
      </el-tab-pane>
      <el-tab-pane label="职称管理" name="job-level-manage">
        <job-level-manage />
      </el-tab-pane>
      <el-tab-pane label="奖惩规则" name="ec-manage">
        <ec-manage />
      </el-tab-pane>
      <el-tab-pane label="权限组" name="permiss-manage">
        <permiss-manage />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import DepManage from "@/components/sys/DepManage";
import PostManage from "@/components/sys/PostManage";
import JobLevelManage from "@/components/sys/JobLevelManage.vue";
import EcManage from "@/components/sys/EcManage.vue";
import PermissManage from "@/components/sys/PermissManage.vue";
export default {
  name: "SysBasic",
  components: {
    DepManage,
    PostManage,
    JobLevelManage,
    EcManage,
    PermissManage,
  },
  data() {
    return {
      activeName: "dep-manage",
    };
  },
};
</script>

<style scoped>
</style>