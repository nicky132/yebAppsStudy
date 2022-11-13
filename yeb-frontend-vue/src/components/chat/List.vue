<template>
  <div class="list">
    <ul style="padding-left: 0px">
      <li
        v-for="item in admins"
        :class="{
          active: currentSession
            ? item.username === currentSession.username
            : false,
        }"
        @click="changeCurrentSession(item)"
      >
        <img
          class="avatar"
          width="30"
          height="30"
          :alt="item.name"
          :src="item.userFace"
        />
        <el-badge :value="isNew[user.username + '#' + item.username]">
          <p class="name">{{ item.name }}</p>
        </el-badge>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "List",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user")),
    };
  },
  computed: mapState(["admins", "currentSession", "isNew"]),
  methods: {
    changeCurrentSession(currentSession) {
      this.$store.commit("changeCurrentSession", currentSession);
    },
  },
};
</script>

<style scoped lang="scss">
.list {
  li {
    padding: 13px 15px;
    border-bottom: 1px solid #292c33;
    cursor: pointer;
    transition: background-color 0.1s;

    &:hover {
      background-color: rgba(255, 255, 255, 0.03);
    }
    &.active {
      background-color: rgba(255, 255, 255, 0.1);
    }
  }
  .avatar,
  .name {
    vertical-align: middle;
  }
  .avatar {
    border-radius: 2px;
  }
  .name {
    display: inline-block;
    margin: 0 0 0 15px;
  }
}
</style>