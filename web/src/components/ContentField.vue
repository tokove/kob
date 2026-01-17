<template>
    <div class="container content-field">
        <div class="glass-card">
            <div class="card-body">
                <slot>

                </slot>
            </div>
        </div>
    </div>
</template>

<script>

</script>

<style scoped>
.content-field {
    margin-top: 20px;
    display: flex;
    justify-content: center;
}

.glass-card {
    width: 100%;
    max-width: 700px;
    padding: 2rem;
    background: rgba(255, 255, 255, 0.1); /* 半透明背景 */
    backdrop-filter: blur(10px); /* 毛玻璃核心：模糊背景 */
    border-radius: 16px;
    border: 1px solid rgba(255, 255, 255, 0.2);
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    color: rgb(0, 0, 0); /* 文字颜色 */
    position: relative;
}
</style>