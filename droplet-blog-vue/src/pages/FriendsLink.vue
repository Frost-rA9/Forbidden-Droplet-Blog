<!-- 友情链接 -->
<template>
    <div>
        <droplet-header></droplet-header>
        <div class="container">
            <el-row  :gutter="30">
                <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
                    <droplet-friends></droplet-friends>
                    <droplet-message></droplet-message>
                </el-col>
                <el-col :sm="24"  :md="8" >
                    <droplet-rightlist></droplet-rightlist>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import header from '../components/header.vue'
import friends from '../components/friends.vue'
import rightlist from '../components/rightlist.vue'
import message from '../components/message.vue'
    export default {
        name: 'FriendsLink',
        data() { //选项 / 数据
            return {

            }
        },
        methods: { //事件处理器

        },
        components: { //定义组件
            'droplet-header':header,
            'droplet-message':message,
            'droplet-friends':friends,
            'droplet-rightlist':rightlist,
        },
        created() { //生命周期函数

        }
    }
</script>

<style>

</style>
