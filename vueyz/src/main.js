import Vue from 'vue';
import App from './App.vue';
import router from './router/index'; //这个router的下级列表可不写
import store from './store';
import VueProgressBar from 'vue-progressbar'; // 进度条
import {
    Aside,
    Badge,
    Breadcrumb,
    BreadcrumbItem,
    Button,
    Card,
    Checkbox,
    Col,
    Collapse,
    CollapseItem,
    Container,
    DatePicker,
    Dialog,
    Drawer,
    Dropdown,
    DropdownItem,
    DropdownMenu,
    Footer,
    Form,
    FormItem,
    Header,
    Icon,
    Input,
    InputNumber,
    Loading,
    Main,
    Menu,
    MenuItem,
    MessageBox,
    Option,
    Pagination,
    Popover,
    Radio,
    RadioGroup,
    Rate,
    Row,
    Select,
    Slider,
    Step,
    Steps,
    Submenu,
    Switch,
    Table,
    TableColumn,
    TabPane,
    Tabs,
    Tag,
    Tooltip,
    Tree,
    Upload
} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {deleteRequest, getRequest, postKeyValueRequest, postRequest, putRequest} from "./utils/api";
import {initMenu} from "./utils/menus";
import 'font-awesome/css/font-awesome.min.css';
import jquery from 'jquery';
import VueRouter from 'vue-router';

// collapse 展开折叠
import CollapseTransition from 'element-ui/lib/transitions/collapse-transition';


Vue.prototype.$ELEMENT = {size: 'small', zIndex: 3000};
Vue.component(CollapseTransition.name, CollapseTransition);
Vue.use(Switch);
Vue.use(CollapseItem);
Vue.use(Radio);
Vue.use(Rate);
Vue.use(RadioGroup);
Vue.use(DatePicker);
Vue.use(Upload);
Vue.use(Row);
Vue.use(Col);
Vue.use(Option);
Vue.use(Submenu);
Vue.use(MenuItem);
Vue.use(Header);
Vue.use(Footer);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Aside);
Vue.use(Main);
Vue.use(Checkbox);
Vue.use(FormItem);
Vue.use(Collapse);
Vue.use(Popover);
Vue.use(Menu);
Vue.use(Tabs);
Vue.use(TabPane);
Vue.use(Breadcrumb);
Vue.use(BreadcrumbItem);
Vue.use(Dropdown);
Vue.use(Steps);
Vue.use(Step);
Vue.use(Tooltip);
Vue.use(Tree);
Vue.use(Pagination);
Vue.use(Badge);
Vue.use(Loading);
Vue.use(Button);
Vue.use(Input);
Vue.use(InputNumber);
Vue.use(Table);
Vue.use(TableColumn);
Vue.use(Dialog);
Vue.use(Card);
Vue.use(Container);
Vue.use(Icon);
Vue.use(Select);
Vue.use(Form);
Vue.use(Tag);
Vue.use(Drawer);
Vue.use(Slider);
Vue.prototype.$alert = MessageBox.alert
Vue.prototype.$confirm = MessageBox.confirm

Vue.prototype.postRequest = postRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.getRequest = getRequest;

Vue.config.productionTip = false;           // 关闭生产模式下给出的提示

Vue.use(VueProgressBar, { // 进度条
    color: 'rgb(143, 255, 199)',
    failedColor: 'red',
    height: '4px'
});


window.jquery = window.$ = jquery;
if (typeof window.gettext !== 'function') {
    window.gettext = function gettext(string) {
        return string;
    };
}


const routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return routerPush.call(this, location).catch(error => error);
}


router.beforeEach((to, from, next) => {
    if (to.path == '/') {
        next();
    } else {
        if (window.sessionStorage.getItem("user")) {
            initMenu(router, store);
            next();
        } else {
            next('/?redirect=' + to.path);
        }
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
