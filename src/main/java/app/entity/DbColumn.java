package app.entity;


import lombok.Data;

@Data
public class DbColumn {

	private String columnName;// 列名
	private String columnDefault;// 列默认值
	private String columnIsNullable;// 是否可以为空
	private String columnDataType;// 数据类型
	private Integer columnCharacterMaximumLength;// 字符最大长度
	private String columnType;// 列类型
	private String columnKey;// 列键
	private String columnExtra;// 列键额外的信息
	private String columnComment;// 列的说明
	
	
	public DbColumn(String columnName, String columnDefault, String columnIsNullable, String columnDataType,
			Integer columnCharacterMaximumLength, String columnType, String columnKey, String columnExtra,
			String columnComment) {
		this.columnName = columnName;
		this.columnDefault = columnDefault;
		this.columnIsNullable = columnIsNullable;
		this.columnDataType = columnDataType;
		this.columnCharacterMaximumLength = columnCharacterMaximumLength;
		this.columnType = columnType;
		this.columnKey = columnKey;
		this.columnExtra = columnExtra;
		this.columnComment = columnComment;
	}

	@Override
	public String toString() {
		return "DbColumn [columnName=" + columnName + ", columnDefault=" + columnDefault + ", columnIsNullable="
				+ columnIsNullable + ", columnDataType=" + columnDataType + ", columnCharacterMaximumLength="
				+ columnCharacterMaximumLength + ", columnType=" + columnType + ", columnKey=" + columnKey
				+ ", columnExtra=" + columnExtra + ", columnComment=" + columnComment + "]";
	}

}
