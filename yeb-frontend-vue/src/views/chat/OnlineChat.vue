<template>
  <div id="app">
    <div class="sidebar">
      <card></card>
      <list></list>
    </div>
    <div class="main">
      <message></message>
      <user-text></user-text>
    </div>
  </div>
</template>

<script>
import Card from "@/components/chat/Card";
import List from "@/components/chat/List";
import UserText from "@/components/chat/UserText";
import Message from "@/components/chat/Message";

export default {
  name: "OnlineChat",
  components: { Card, List, UserText, Message },
  mounted: function () {
    this.$store.dispatch("initData");
  },
};
</script>

<style lang="scss" scoped>
#app {
  margin: 20px auto;
  width: 800px;
  height: 600px;
  overflow: hidden;
  border-radius: 6px;

  .sidebar,
  .main {
    height: 100%;
  }

  .sidebar {
    float: left;
    width: 200px;
    color: #f4f4f4;
    background-color: #2e3238;
  }
  .main {
    position: relative;
    overflow: hidden;
    background-color: #eee;
  }
  .text {
    position: absolute;
    width: 100%;
    bottom: 0;
    left: 0;
  }
  .message {
    height: "calc(100% - 160px)";
  }
}
</style>