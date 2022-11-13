<template>
  <div class="card">
    <header>
      <img
        class="avatar"
        width="45"
        height="45"
        :alt="user.name"
        :src="user.userFace"
      />
      <p class="name">{{ user.name }}</p>
    </header>
    <footer>
      <input
        class="search"
        type="text"
        v-model="$store.state.filterKey"
        placeholder="search user..."
      />
    </footer>
  </div>
</template>
<script>
export default {
  name: "Card",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user")),
    };
  },
};
</script>

<style lang="scss" scoped>
.card {
  padding: 12px;
  .avatar {
    width: 40px;
    height: 40px;
    vertical-align: middle; /*这个是图片和文字居中对齐*/
  }
  .name {
    display: inline-block;
    padding: 10px;
    margin-bottom: 15px;
    font-size: 16px;
  }
  .search {
    background: #26292e;
    height: 30px;
    line-height: 30px;
    padding: 0 10px;
    border: 1px solid #3a3a3a;
    border-radius: 4px;
    outline: none; /*鼠标点击后不会出现蓝色边框*/
    color: #fff;
  }
}
</style>