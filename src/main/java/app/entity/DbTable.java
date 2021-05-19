package app.entity;


import lombok.Data;

@Data
public class DbTable {

	private String tableName;
	private Integer tableRows;
	private String tableType;// 表的类型"BASE TABLE" "VIEW"

	public enum FieldName {
		tableName, tableRows, tableType
	}

	public DbTable() {

	}

	public DbTable(String tableName, Integer tableRows, String tableType) {

		this.tableName = tableName;
		this.tableRows = tableRows;
		this.tableType = tableType;

	}

	@Override
	public String toString() {
		return "DbTable [tableName=" + tableName + ", tableRows=" + tableRows + ", tableType=" + tableType + "]";
	}

}
