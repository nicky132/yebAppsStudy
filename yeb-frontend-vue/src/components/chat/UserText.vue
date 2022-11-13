<template>
  <div class="usertext">
    <textarea
      placeholder="按 Ctrl + Enter 发送"
      v-model="content"
      @keyup="addMessage"
    ></textarea>
  </div>
</template>
<script>
import { mapState } from "vuex";
export default {
  name: "UserText",
  data() {
    return {
      content: "",
    };
  },
  computed: mapState(["currentSession"]),
  methods: {
    addMessage(e) {
      if (e.ctrlKey && e.keyCode === 13 && this.content.length) {
        let obj = new Object();
        obj.to = this.currentSession.username;
        obj.content = this.content;
        this.$store.state.stomp.send("/ws/chat", {}, JSON.stringify(obj));
        this.$store.commit("addMessage", obj);
        this.content = "";
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.usertext {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 100%;
  height: 30%;
  border: solid 1px grey;
  textarea {
    padding: 10px;
    height: 89%;
    width: 97%;
    border: none;
    outline: none;
    font-family: "Micrsofot Yahei";
    resize: none;
  }
}
</style>