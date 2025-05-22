package TripPackageAutomation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExcelUtil
{
	public static int writeToExcel(List<WebElement> packageNames,List<WebElement> prices,int j,int k,WebDriver driver) throws IOException
	{
		FileInputStream file=new FileInputStream("output.xlsx");
		
		XSSFWorkbook wb=new XSSFWorkbook(file);
		XSSFSheet sheet=wb.getSheet("Sheet1");
		
		for(int i=0;i<packageNames.size();i++)
		{
			XSSFRow r=sheet.createRow(i+k);
			XSSFCell c=r.createCell(0);
			c.setCellValue(packageNames.get(i).getText());
			XSSFCell c2=r.createCell(1);
			c2.setCellValue(prices.get(i).getText());
		}
			FileOutputStream file1=new FileOutputStream("output.xlsx");
			
			k+=prices.size();
			wb.write(file1);
			wb.close();
			file.close();
		return k;
	}
}
