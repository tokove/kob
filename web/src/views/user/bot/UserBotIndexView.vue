<template>
    <ContentField>
        我的Bot
    </ContentField>
</template>

<script>
import ContentField from "@/components/ContentField.vue";
import $ from "jquery"

export default {
    components: {
        ContentField
    },
    setup() {
        $.ajax({
            url: "http://localhost:3000/user/bot/add/",
            type: "post",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + localStorage.getItem("jwt_token")
            },
            data: JSON.stringify({
                title: "我的bot",
                description: "我的超级bot",
                content: "int main",
            }),
            success(resp) {
                console.log(resp);
            },
            error(resp) {
                console.log(resp);
            }
        }),
        $.ajax({
            url: "http://localhost:3000/user/bot/remove/",
            type: "post",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + localStorage.getItem("jwt_token")
            },
            data: JSON.stringify({
                bot_id: 5,
            }),
            success(resp) {
                console.log(resp);
            },
            error(resp) {
                console.log(resp);
            }
        })
    }
}
</script>

<style scoped>
    
</style>