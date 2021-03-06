package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleModel extends Model{

	private String name;
	
	public RoleModel(int id) {
		super(id);
	}

	public RoleModel() {
	}
	
	public RoleModel(int id, String name) {
		super(id);
		this.name = name;
	}

	//method untuk mengubah data yang diambil dari database, yaitu dari Object ResultSet menjadi Object Role
	@Override
	public Model convData(ResultSet rawData) {
		try {
			return new RoleModel(rawData.getInt("id"), rawData.getString("name"));
		} catch (SQLException e) {
			return null;
		}
	}

	//method untuk menambahkan data kedalam database
	@Override
	public Model insertData() {
		PreparedStatement query = db.prepareStatement("INSERT INTO Role(id,name) VALUES(?,?) ");
		try {
			query.setInt(1, getId());
			query.setString(2, this.name);
			PreparedStatement getInserted = db.prepareStatement("SELECT MAX(id) AS id FROM Role");
			ResultSet res = getInserted.executeQuery();
			this.setId(res.getInt("id"));
			
			return this;
		} catch (SQLException e) {
			return null;
		}
	}

	//method untuk mengupdate data baru kedalam database
	@Override
	public Model updateData() {
		PreparedStatement query = db.prepareStatement("UPDATE Role SET name=? WHERE id=? ");
		try {
			query.setString(1, this.name);
			query.setInt(2, getId());
			query.execute();
			return this;
		} catch (SQLException e) {
			return null;
		}
	}

	//method untuk menghapus data Role dari database
	@Override
	public Boolean deleteData() {
		PreparedStatement query = db.prepareStatement("DELETE FROM Role WHERE id=?");
		try {
			query.setInt(1, getId());
			query.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	//method untuk mencari data role yang ada pada database berdasarkan id-nya
	@Override
	public Model findData(int id) {
		PreparedStatement query = db.prepareStatement("SELECT * FROM Role WHERE id=?");
		try {
			query.setInt(1, id);
			ResultSet data = query.executeQuery();
			data.next();
			return convData(data);
		} catch (SQLException e) {
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
