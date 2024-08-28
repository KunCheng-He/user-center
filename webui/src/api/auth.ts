import request from "@/utils/request";

const AUTH_BASE_URL = "/user";

class AuthAPI {
  /** 登录 接口*/
  static login(data: LoginData) {
    const formData = new FormData();
    formData.append("telPhone", data.telPhone);
    formData.append("userPassword", data.userPassword);
    return request<any, LoginResult>({
      url: `${AUTH_BASE_URL}/login`,
      method: "post",
      data: formData,
    });
  }

  /** 注销 接口*/
  static logout() {
    return request({
      url: `${AUTH_BASE_URL}/logout`,
      method: "delete",
    });
  }
}

export default AuthAPI;

/** 登录请求参数 */
export interface LoginData {
  /** 用户名 */
  telPhone: string;
  /** 密码 */
  userPassword: string;
}

/** 登录响应 */
export interface LoginResult {
  /** 访问token */
  accessToken?: string;
  /** 过期时间(单位：毫秒) */
  expires?: number;
  /** 刷新token */
  refreshToken?: string;
  /** token 类型 */
  tokenType?: string;
}
