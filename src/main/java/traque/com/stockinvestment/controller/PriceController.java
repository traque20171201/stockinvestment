package traque.com.stockinvestment.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import traque.com.stockinvestment.entity.Price;
import traque.com.stockinvestment.model.PriceRepository;

@Controller
@RequestMapping("/price")
public class PriceController {
	
	@Autowired
	private PriceRepository priceRepository;

	@GetMapping("/history")
	public String history(Model model) {
		model.addAttribute("prices", priceRepository.findAll());
		return "price/history";
	}
	
	@GetMapping("/fluctuate")
	public String fluctuate(Model model) {
		return "price/fluctuate";
	}
	
	@GetMapping("/import")
	public String importExcel(Model model) {
		return "price/import";
	}
	
	@PostMapping("/import")
	public String doImportExcel(@RequestParam("exchange") Integer exchange, 
			@RequestParam("fileExcel") MultipartFile fileExcel, Model model) throws IOException, InvalidFormatException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Price> priceList = new ArrayList<Price>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			for(int i=3;i<worksheet.getPhysicalNumberOfRows()+1 ;i++) {
				Price price = new Price();
				XSSFRow row = worksheet.getRow(i);
				price.setExchange(exchange);
				price.setDate(formatter.parse(row.getCell(0).getStringCellValue()));
				price.setStock(row.getCell(1).getStringCellValue());
				price.setRefer((float) row.getCell(2).getNumericCellValue());
				price.setCeiling((float) row.getCell(3).getNumericCellValue());
				price.setFloor((float) row.getCell(4).getNumericCellValue());
				price.setOpen((float) row.getCell(5).getNumericCellValue());
				price.setClose((float) row.getCell(6).getNumericCellValue());
				price.setHighest((float) row.getCell(7).getNumericCellValue());
				price.setLowest((float) row.getCell(8).getNumericCellValue());
				price.setAvge((float) row.getCell(9).getNumericCellValue());
				price.setChange_value((float) row.getCell(10).getNumericCellValue());
				price.setChange_percent((float) row.getCell(11).getNumericCellValue());
				price.setVolume_order(row.getCell(12).getNumericCellValue());
				price.setVolume_put(row.getCell(13).getNumericCellValue());
				price.setVolume_total(row.getCell(14).getNumericCellValue());
				price.setValue_order(row.getCell(15).getNumericCellValue());
				price.setValue_put(row.getCell(16).getNumericCellValue());
				price.setValue_total(row.getCell(17).getNumericCellValue());
				priceList.add(price);
			}
			model.addAttribute("prices", priceList);
			workbook.close();
			priceRepository.saveAll(priceList);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "price/history";
	}
}
