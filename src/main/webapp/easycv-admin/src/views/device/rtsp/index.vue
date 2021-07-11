<template>
  <div class="app-container">
    <el-row>
      <el-col :span="4">
        <el-button
          class="filter-item"
          style="margin-left: 10px;"
          type="primary"
          icon="el-icon-edit">
          添加rtsp流
        </el-button>

      </el-col>
    </el-row>

    <el-row>
      <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="Loading"
        border
        fit
        highlight-current-row
        align="center"
      >
        <el-table-column align="center" label="ID" width="95">
          <template slot-scope="scope">
            {{ scope.$index }}
          </template>
        </el-table-column>
        <el-table-column label="rtsp地址">
          <template slot-scope="scope">
            {{ scope.row.imgRecordTask.playUrl }}
          </template>
        </el-table-column>
        <el-table-column label="路径" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.imgRecordTask.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="图片" align="center">
          <template slot-scope="scope">
            <img width="60" height="60" src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png">
          </template>
        </el-table-column>
        <el-table-column label="状态"  align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.imgRecordTask.status == 1">标签一</el-tag>
            <el-tag v-if="scope.row.imgRecordTask.status == 2">标签二</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作"  align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              v-if="scope.row.imgRecordTask.status == -1"
              :loading="scope.row.imgRecordTask.status == 0"
              @click="handleStart(scope.$index, scope.row)">启动</el-button>
            <el-button
              size="mini"
              type="warning"
              v-if="scope.row.imgRecordTask.status == 1"
              @click="handleClose(scope.$index, scope.row)">关闭</el-button>
            <el-button
              size="mini"
              type="danger"
              disabled
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
  </div>

</template>


<script>
import { add,stop,list,start } from '@/api/record'

export default {
  data() {
    return {
      list: null,
      listLoading: true
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      list().then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    handleStart(index,row){
      start(row.imgRecordTask.id).then(response => {
        if (response.code == 20000){
          this.$notify({
            title: '成功',
            message: '开启成功',
            type: 'success'
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: "开启失败"
          });
        }
        this.fetchData()
      })
    },
    handleClose(index,row){
      row.imgRecordTask.status = 0
      stop(row.imgRecordTask.id).then(response => {
        if (response.code == 20000){
          this.$notify({
            title: '成功',
            message: '关闭成功',
            type: 'success'
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: "关闭失败"
          });
        }
        this.fetchData()
      })
    },
    handleDelete(index,row){

    }
  }
}
</script>

<style>
.el-row {
  margin-bottom: 20px;
&:last-child {
   margin-bottom: 0;
 }
}
</style>
