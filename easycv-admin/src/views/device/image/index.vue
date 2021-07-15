<template>
  <div class="app-container">
    <el-row>
      <el-col :span="4">
        <el-button
          class="filter-item"
          style="margin-left: 10px;"
          type="primary"
          icon="el-icon-edit"
          @click="uploadDialogOpen">
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
            {{scope.row.name}}
          </template>
        </el-table-column>
        <el-table-column label="图片" align="center">
          <template slot-scope="scope">
            <el-popover placement="right" title trigger="hover">
              <img :src="scope.row.path" style="height: 200px;width: 300px" align="center" />
              <img slot="reference" :src="scope.row.path"  style="max-height: 50px;max-width: 130px"
              />
            </el-popover>
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
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>


    <el-dialog title= "上传图片" style="text-align: center" :visible.sync="dialogFormVisible">
      <el-upload
        class="upload-demo"
        drag
        ref="upload"
        :action="uploadPath"
        multiple
        name="files"
        :auto-upload="false"
        :file-list="fileList"
        :on-success="uploadSuccess"
        :before-upload="beforeUpload">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="submitUpload">上传</el-button>
        <el-button @click="uploadDialogClose">关闭</el-button>
      </div>
    </el-dialog>
  </div>

</template>


<script>
import {list,deleteImage} from '@/api/img'
import {add} from "@/api/record"

export default {
  data() {
    return {
      list: null,
      listLoading: true,
      dialogFormVisible: false,
      uploadPath: "",
      fileList:[]
    }
  },
  created() {
    this.fetchData()
    this.uploadPath = process.env.VUE_APP_BASE_API + "/images/upload"
  },
  methods: {
    fetchData() {
      this.listLoading = true
      list().then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    submitUpload(){
      this.$refs.upload.submit();
    },
    handleAddTask(index, row){
      add(row.id).then(response => {
        let msg = response.message;
        if (response.code == 20000){
          this.$notify({
            title: '成功',
            message: '添加成功',
            type: 'success'
          });
          this.fetchData()
        } else {
          this.$notify.error({
            title: '失败',
            message: msg
          });
        }
      })
    },
    uploadDialogOpen(){
      this.dialogFormVisible = true
    },
    uploadDialogClose(){
      this.fileList = []
      this.dialogFormVisible = false

    },
    handleDelete(index, row){
      var ids = [];
      ids.push(row.id)
      deleteImage(ids).then(response => {
        let msg = response.message
        if (response.code == 20000){
          this.$notify({
            title: '成功',
            message: msg,
            type: 'success'
          });
          this.fetchData()
        } else {
          this.$notify.error({
            title: '失败',
            message: msg
          });
        }
      })
    },
    beforeUpload(file){
      console.log("上传之前")
    },
    uploadSuccess(response, file, fileList){
      let msg = response.message;
      if (response.code==20000){
        this.$notify({
          title: '成功',
          message: '上传成功',
          type: 'success'
        });
        this.fetchData()
      } else {
        this.$notify.error({
          title: '失败',
          message: msg
        });
      }
      this.dialogFormVisible = false
      this.fetchData()
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
