<template>
  <div class="login-container">
    <!-- 顶部工具栏 -->
    <div class="top-bar">
      <el-switch v-model="isDark" inline-prompt active-icon="Moon" inactive-icon="Sunny" @change="toggleTheme" />
      <lang-select class="ml-2 cursor-pointer" />
    </div>
    <!-- 登录表单 -->
    <el-card class="login-card">
      <div class="text-center relative">
        <h2>{{ defaultSettings.title }}</h2>
        <el-tag class="ml-2 absolute-rt">{{ defaultSettings.version }}</el-tag>
      </div>

      <el-form ref="loginFormRef" :model="loginData" :rules="loginRules" class="login-form">
        <!-- 电话号码 -->
        <el-form-item prop="telPhone">
          <div class="input-wrapper">
            <i-ep-phone class="mx-2" />
            <el-input ref="telPhone" v-model="loginData.telPhone" :placeholder="$t('电话号码')" name="telPhone" size="large"
              class="h-[48px]" />
          </div>
        </el-form-item>

        <!-- 密码 -->
        <el-tooltip :visible="isCapslock" :content="$t('login.capsLock')" placement="right">
          <el-form-item prop="userPassword">
            <div class="input-wrapper">
              <i-ep-lock class="mx-2" />
              <el-input v-model="loginData.userPassword" :placeholder="$t('密码')" type="userPassword" name="userPassword"
                @keyup="checkCapslock" @keyup.enter="handleLoginSubmit" size="large" class="h-[48px] pr-2"
                show-password />
            </div>
          </el-form-item>
        </el-tooltip>

        <!-- 登录按钮 -->
        <el-button :loading="loading" type="primary" size="large" class="w-full" @click.prevent="handleLoginSubmit">
          {{ $t("login.login") }}
        </el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
// 外部库和依赖
import { LocationQuery, useRoute } from "vue-router";

// 内部依赖
import { useSettingsStore, useUserStore } from "@/store";
import AuthAPI, { LoginData } from "@/api/auth";
import router from "@/router";
import defaultSettings from "@/settings";
import { ThemeEnum } from "@/enums/ThemeEnum";

// 类型定义
import type { FormInstance } from "element-plus";

// 导入 login.scss 文件
import "@/styles/login.scss";

// 使用导入的依赖和库
const userStore = useUserStore();
const settingsStore = useSettingsStore();
const route = useRoute();
// 窗口高度
const { height } = useWindowSize();
// 国际化 Internationalization
const { t } = useI18n();

// 是否暗黑模式
const isDark = ref(settingsStore.theme === ThemeEnum.DARK);
// 按钮 loading 状态
const loading = ref(false);
// 是否大写锁定
const isCapslock = ref(false);
// 登录表单ref
const loginFormRef = ref<FormInstance>();

const loginData = ref<LoginData>({
  telPhone: "",
  userPassword: "",
} as LoginData);

const loginRules = computed(() => {
  return {
    telPhone: [
      {
        required: true,
        trigger: "blur",
        message: t("请输入电话号码"),
      },
    ],
    userPassword: [
      {
        required: true,
        trigger: "blur",
        message: t("请输入登录密码"),
      },
      {
        min: 8,
        message: t("密码长度不可小于8位"),
        trigger: "blur",
      },
    ],
  };
});

/** 登录表单提交 */
function handleLoginSubmit() {
  loginFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      loading.value = true;
      userStore
        .login(loginData.value)
        .then(() => {
          const { path, queryParams } = parseRedirect();
          router.push({ path: path, query: queryParams });
        })
        .catch(() => {
          // 登录失败
          console.log("登录失败");
        })
        .finally(() => {
          loading.value = false;
        });
    }
  });
}

/** 解析 redirect 字符串 为 path 和  queryParams */
function parseRedirect(): {
  path: string;
  queryParams: Record<string, string>;
} {
  const query: LocationQuery = route.query;
  const redirect = (query.redirect as string) ?? "/";

  const url = new URL(redirect, window.location.origin);
  const path = url.pathname;
  const queryParams: Record<string, string> = {};

  url.searchParams.forEach((value, key) => {
    queryParams[key] = value;
  });

  return { path, queryParams };
}

/** 主题切换 */
const toggleTheme = () => {
  const newTheme =
    settingsStore.theme === ThemeEnum.DARK ? ThemeEnum.LIGHT : ThemeEnum.DARK;
  settingsStore.changeTheme(newTheme);
};

/** 检查输入大小写 */
function checkCapslock(event: KeyboardEvent) {
  // 防止浏览器密码自动填充时报错
  if (event instanceof KeyboardEvent) {
    isCapslock.value = event.getModifierState("CapsLock");
  }
}
</script>

<style lang="scss" scoped></style>
