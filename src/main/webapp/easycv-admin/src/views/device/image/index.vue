<template>
  <div class="app-container">
    <el-row>
      <el-col :span="4">
        <el-button
          class="filter-item"
          style="margin-left: 10px;"
          type="primary"
          icon="el-icon-edit"
          @click="handleCreate">
          上传图片
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
            {{ scope.row.id }}
          </template>
        </el-table-column>
        <el-table-column label="图片名称">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column label="路径" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.path }}</span>
          </template>
        </el-table-column>
        <el-table-column label="图片" align="center">
          <template slot-scope="scope">
            <img width="60" height="60" src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png">
          </template>
        </el-table-column>
        <el-table-column label="上传时间"  align="center">
          <template slot-scope="scope">
            {{ scope.row.uploadTime }}
          </template>
        </el-table-column>
        <el-table-column label="操作"  align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              :disabled="scope.row.taskNum == 1"
              @click="handleAddTask(scope.$index, scope.row)">添加流</el-button>
            <el-button
              size="mini"
              type="danger"
              disabled
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>


    <el-dialog title= "上传图片" style="text-align: center" :visible.sync="dialogFormVisible">
      <el-upload
        class="upload-demo"
        drag
        action="http://localhost:9500/images/upload"
        multiple
        :file-list="attachList"
        :on-success="uploadSuccess"
        :before-upload="beforeUpload">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>

</template>


<script>
import {list,hasTask} from '@/api/img'
import {add} from "@/api/record";

export default {
  data() {
    return {
      list: null,
      listLoading: true,
      dialogFormVisible: false
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
    handleAddTask(index, row){
      add(row.id).then(response => {
        if (response.data.code == 20000){
          this.$notify({
            title: '成功',
            message: '添加成功',
            type: 'success'
          });
          this.fetchData()
        } else {
          this.$notify.error({
            title: '失败',
            message: '添加失败'
          });
        }
      })
    },
    handleCreate(){
      this.dialogFormVisible = true
    },
    beforeUpload(file){
      console.log("上传之前")
    },
    uploadSuccess(response, file, fileList){
      console.log("上传成功")
      this.dialogFormVisible = false
      this.fetchData()
    },
    beforeRemove(file, fileList){
      console.log('我是before-remove钩子函数，我被调用了');
    },
    handleRemove(file, fileList){
      console.log('我是on-remove钩子函数，我被调用了');
    },
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
