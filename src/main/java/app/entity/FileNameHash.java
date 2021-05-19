package app.entity;

import java.util.List;

import lombok.Data;

/***
 * 学生提交文件后，需要删除文件时，计算文件名hash值
 * 
 * @author Denny
 * 
 */
@Data
public class FileNameHash {
	private String filename;
	private String filehash;
	private Long filesize;// 字节数
}
