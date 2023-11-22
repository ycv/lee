<template>
  <el-container class="home-container">
    <!--头部-->
    <el-header class="homeHeader">
      <div class="title">统计数据</div>
      <div>
        <el-button icon="el-icon-bell" type="text" style="margin-right: 8px;color: #000000;" size="normal"
                   @click="goChat"></el-button>
        <el-dropdown class="userInfo" @command="commandHandler">
            <span class="el-dropdown-link">
              {{ user.name }}<i><img :src="require('@/assets/' + user.userface)" alt=""/></i>
            </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
            <el-dropdown-item command="setting">设置</el-dropdown-item>
            <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>

    <!--主体-->
    <el-container :style="{height: containerHeight}">

      <!-- 左侧菜单 -->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu
            background-color="#313743"
            text-color="#fff"
            active-text-color="#409FFF"
            :unique-opened="true"
            :collapse="isCollapse"
            :collapse-transition="false"
            :router="true"
            :default-active="activePath">

          <el-menu-item index="/home">
            <i class="el-icon-s-custom"></i>
            <span slot="title">首页</span>
          </el-menu-item>


          <!--一级菜单-->
          <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="item.enabled" :key="item.id">
            <!--一级菜单的模板区域-->
            <template slot="title">
              <i style="color: #409eff;margin-right: 5px" :class="item.iconCls"></i>
              <span>{{ item.name }}</span>
            </template>

            <!--二级菜单-->
            <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" v-if="child.enabled"
                          :key="child.id" @click="saveNavState(child.path)">
              <template slot="title">
                <!--                <i class="el-icon-menu"></i>-->
                <i :class="child.iconCls"></i>
                <span>&nbsp;&nbsp;{{ child.name }}</span>
              </template>
            </el-menu-item>
          </el-submenu>


        </el-menu>
      </el-aside>


      <el-container>
        <!-- 右侧内容 -->
        <el-main :style="{height: containerFooterHeight}">
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path !='/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$router.currentRoute.name }}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
            欢迎来到统计数据！
          </div>
          <router-view class="homeRouterView"/>
        </el-main>
        <!--底部-->
        <el-footer>

        </el-footer>
      </el-container>

    </el-container>

  </el-container>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      containerHeight: "",
      containerFooterHeight: "",
      activePath: '',// 被激活的链接地址
      isCollapse: false
      // user: JSON.parse(window.sessionStorage.getItem("user"))
    }
  },
  computed: {
    routes() {
      return this.$store.state.routes;
    },
    user() {
      return this.$store.state.currentHr;
    }
  },


  watch: {
    // 监听路由变化 处理两个组件之间的相互跳转，右侧页面产生变化，但左侧菜单选中状态未刷新
    $route(to, from) {
      this.activePath = to.path;
    }
  },


  mounted() {
    //动态调整左侧菜单栏高度
    this.containerHeight = document.documentElement.clientHeight - 61 + "px";
    this.containerFooterHeight = document.documentElement.clientHeight - 61 - 22 + "px";
  },


  //  async created() {
  created() {
    for (const item of this.routes) {
      if (item.children) {
        for (const subItem of item.children) {
          if (subItem.path === this.$route.path) {
            this.activePath = subItem.path;
          }
        }
      }
    }
  },

  methods: {

    // 保存当前激活的路径
    saveNavState(activePath) {
      // 保存到sessionStorage中是为了在刷新的时候能够取出这个路径并将对应的菜单高亮
      // sessionStorage.setItem('activePath', activePath);
      this.activePath = activePath;
    },
    // 点击按钮切换左侧菜单的折叠与展开
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
    },

    goChat() {
      this.$router.push("/chat");
    },
    commandHandler(cmd) {
      if (cmd == 'logout') {
        this.$confirm('1此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest("/logout");
          window.sessionStorage.removeItem("user")
          this.$store.commit('initRoutes', []);
          this.$router.replace("/");
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          });
        });
      } else if (cmd == 'userinfo') {
        this.$router.push('/hrinfo');
      }
    }
  }
}
</script>

<style scoped>

.homeRouterView {
  margin-top: 4px;
}

.homeWelcome {
  text-align: center;
  font-size: 30px;
  font-family: 华文行楷;
  color: #409eff;
  padding-top: 50px;
}

.homeHeader {
  background-color: #409eff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0px 15px;
  box-sizing: border-box;
}

.homeHeader .title {
  font-size: 30px;
  font-family: 华文行楷;
  color: #ffffff
}

.homeHeader .userInfo {
  cursor: pointer;
}

.el-dropdown-link img {
  width: 48px;
  height: 48px;
  border-radius: 24px;
  margin-left: 8px;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
}


.home-container {
  height: 100%;
}

.el-header {
  background-color: rgb(54, 61, 64);
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;
}

.header-logo {
  display: flex;
  align-items: center;
}

.header-logo img {
  height: 50px;
}

/* 左侧菜单 */
.el-aside {
  background-color: rgb(49, 55, 67);
  color: white;
  user-select: none;
  transition: width 0.3s;
}

.iconfont {
  margin-right: 10px;
}

.el-menu {
  border-right: none;
}

.toggle-button {
  background-color: #4A5064;
  font-size: 14px;
  text-align: center;
  line-height: 30px;
  letter-spacing: 2px;
  cursor: pointer;
}

/* 右侧内容区 */
.el-main {
  background-color: rgb(233, 237, 241);
  padding: 5px;
}


.el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  height: 22px !important;
  line-height: 22px;
}

</style>