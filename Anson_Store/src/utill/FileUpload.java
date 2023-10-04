package utill;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import model.ItemBean;

public class FileUpload{



	public ItemBean fileUp(HttpServletRequest request,String path)  {
		ItemBean regiBean = new ItemBean();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		factory.setSizeThreshold(300000000);

		try {

			List<FileItem> fileitem =  upload.parseRequest(request);


			Iterator<FileItem> iterator = fileitem.iterator();

			while(iterator.hasNext()) {

				FileItem item = iterator.next();

				if(!item.isFormField()) {
					if(extensionCheck(item.getName())) {
						regiBean.setItemImage(item.getName());
						item.write(new File(path + "/" + item.getName()));
					}

				}else {
					String fieldName =	item.getFieldName();

					switch (fieldName) {
					case "itemNo":
						regiBean.setItemNo(Integer.parseInt(item.getString("UTF-8")));
						break;
					case "itemName":
						regiBean.setItemName(item.getString("UTF-8"));
						break;
					case "itemPrice":
						regiBean.setItemPrice(Integer.parseInt(item.getString("UTF-8")));
						break;
					case "stockCount":
						regiBean.setStockCount(Integer.parseInt(item.getString("UTF-8")));
						break;
					case "specialItem":
						regiBean.setSpecialItem(Integer.parseInt(item.getString("UTF-8")));
						break;
					case "updateTime":

						SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS");
						System.out.println("fileupload:"+item.getString());
						Date date = sdf.parse(item.getString());
						long millis = date.getTime();
//						String date = (String)item.getString("UFT-8");
//						Date parsedDate = dateFormat.parse(date);

						regiBean.setUpdateTime(new Timestamp(millis));
						System.out.println("getTime:"+regiBean.getUpdateTime());

						break;
					default:

					}
				}
			}


		}catch (FileUploadException e1) {
			e1.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return regiBean;
	}

	public String csvFileUp(HttpServletRequest request,String path)  {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String csvName="";
		try {
			List<FileItem> fileitem =  upload.parseRequest(request);
			Iterator<FileItem> iterator = fileitem.iterator();
			FileItem item = iterator.next();

			if(!item.isFormField()) {
				if(csvCheck(item.getName())) {
					item.write(new File(path + "/" + item.getName()));
					csvName = item.getName();
				}else{
					System.out.println("not csv");
					return "";
				}

			}
			System.out.println("csv");
		}catch (FileUploadException e1) {
			e1.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return csvName;
	}



	public boolean extensionCheck(String fileName) {
		String ext = "";

		if (fileName== null || fileName.equals("")){
			return false;
		}

		int index=fileName.lastIndexOf('.');
		if(index > 0 ) {
			ext = fileName.substring(index+1);
		}

		if(ext.equals("png") || ext.equals("jpeg") || ext.equals("jpg"))return true;

		return false;


	}


	public boolean csvCheck(String fileName) {
		String ext = "";

		if (fileName== null || fileName.equals("")){
			return false;
		}

		int index=fileName.lastIndexOf('.');
		if(index > 0 ) {
			ext = fileName.substring(index+1);
		}

		if(ext.equals("csv"))return true;

		return false;

	}

}
