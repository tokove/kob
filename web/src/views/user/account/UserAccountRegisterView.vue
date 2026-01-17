<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="login-form text-center">
                <form @submit.prevent="register">
                <div class="mb-3">
                    <label for="username" class="form-label">用户名</label>
                    <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">密码</label>
                    <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">确认密码</label>
                    <input v-model="confirmPassword" type="password" class="form-control" id="confirmPassword" placeholder="请再次输入密码">
                </div>
                <div class="error-message"> {{ error_message }}</div>
                <button type="text" class="btn btn-primary">注册</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from "@/components/ContentField.vue";
import { ref } from 'vue'
import router from '@/router/index' 
import $ from 'jquery'

export default {
    components: {
        ContentField
    },
    setup() {
        let username = ref('');
        let password = ref('');
        let confirmPassword = ref('');
        let error_message = ref('');
        
        const register = () => {
            $.ajax({
                url: "http://localhost:3000/user/account/register/",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify({
                    username: username.value,
                    password: password.value,
                    confirmPassword: confirmPassword.value,
                }),
                success(resp) {
                    if(resp.error_message === "success") {
                        error_message.value = "注册成功";
                        router.push({ name: "user_account_login" });
                    } else {
                        error_message.value = resp.error_message;
                    }
                },
            })
        };

        return {
            username,
            password,
            confirmPassword,
            error_message,
            register,
        }
    }
}
</script>

<style scoped>
button {
    width: 30%;
}

.login-form {
  width: 100%;
  max-width: 300px; /* ← 调整这个值控制输入框长度 */
  padding: 0 1rem;   /* 可选：左右留点空隙 */
}

.error-message {
    color: red;
}
</style>