package com.wahyu.todoapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataItem implements Parcelable {

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("task")
	private String task;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private boolean status;

	@SerializedName("updatedAt")
	private String updatedAt;

	public DataItem(Parcel in) {
		createdAt = in.readString();
		task = in.readString();
		id = in.readInt();
		status = in.readByte() != 0;
		updatedAt = in.readString();
	}

	public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
		@Override
		public DataItem createFromParcel(Parcel in) {
			return new DataItem(in);
		}

		@Override
		public DataItem[] newArray(int size) {
			return new DataItem[size];
		}
	};

	public DataItem() {

	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTask(String task){
		this.task = task;
	}

	public String getTask(){
		return task;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(createdAt);
		dest.writeString(task);
		dest.writeInt(id);
		dest.writeByte((byte) (status ? 1 : 0));
		dest.writeString(updatedAt);
	}
}