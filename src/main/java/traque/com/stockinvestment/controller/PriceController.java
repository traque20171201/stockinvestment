package traque.com.stockinvestment.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import traque.com.stockinvestment.entity.Price;
import traque.com.stockinvestment.model.PriceFluctuateRepository;
import traque.com.stockinvestment.model.PriceRepository;
import traque.com.stockinvestment.price.HistorySearch;
import traque.com.stockinvestment.price.StockList;

@Controller
@RequestMapping("/price")
public class PriceController {

	private static final Logger log = LogManager.getLogger(PriceController.class);
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private PriceFluctuateRepository priceFluctuateRepository;

	@GetMapping("/history")
	public String history(Model model) {
		log.info("PriceController history start.");
		// Initialize History Search
		HistorySearch historySearch = new HistorySearch();
		historySearch.setExchange(1);
		historySearch.setStock(null);
		historySearch.setPage(1);
		historySearch.setSize(100);
		Calendar calendar = Calendar.getInstance();
		historySearch.setDate_to(calendar.getTime());
		calendar.add(Calendar.MONTH, -1);
		historySearch.setDate_from(calendar.getTime());
		// Get stock list from file
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<StockList>> typeReference = new TypeReference<List<StockList>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/hose.json");
		try {
			List<StockList> stockList = mapper.readValue(inputStream, typeReference);
			model.addAttribute("stockList", stockList);	
		} catch (IOException e) {
			e.printStackTrace();
		}	
		// Initialize Pagination
		Pageable pageable = PageRequest.of(historySearch.getPage() - 1,historySearch.getSize(),Sort.by("date").descending().and(Sort.by("stock")));
		// Find Price History
		Page<Price> prices = priceRepository.findPriceHistory(historySearch.getExchange(),
				historySearch.getStock(),
				historySearch.getDate_from(),
				historySearch.getDate_to(),
				pageable);
		// Set Attributes For Model
		model.addAttribute("historySearch", historySearch);
		model.addAttribute("prices", prices);
		return "price/history";
	}
	
	@PostMapping("/history")
	public String historySearch(@ModelAttribute("historySearch") HistorySearch historySearch ,Model model) {
		log.info("PriceController historySearch start.");
		// Check Search All With Stock
		if (historySearch.getStock().isEmpty()) {
			historySearch.setStock(null);
		}
		// Get stock list from file
		ObjectMapper mapper = new ObjectMapper();
		String filejson = "/json/hose.json";
		if (historySearch.getExchange().equals(2)) {
			filejson = "/json/hnx.json";
		} else if(historySearch.getExchange().equals(3)) {
			filejson = "/json/upcom.json";
		}
		TypeReference<List<StockList>> typeReference = new TypeReference<List<StockList>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream(filejson);
		try {
			List<StockList> stockList = mapper.readValue(inputStream, typeReference);
			model.addAttribute("stockList", stockList);	
		} catch (IOException e) {
			e.printStackTrace();
		}	
		// Initialize Pagination
		Pageable pageable = PageRequest.of(historySearch.getPage() - 1,historySearch.getSize(),Sort.by("date").descending().and(Sort.by("stock")));
		// Find Price History
		Page<Price> prices = priceRepository.findPriceHistory(historySearch.getExchange(),
				historySearch.getStock(),
				historySearch.getDate_from(),
				historySearch.getDate_to(),
				pageable);
		// Set Attributes For Model
		model.addAttribute("historySearch", historySearch);
		model.addAttribute("prices", prices);
		return "price/history";
	}
	
	@GetMapping("/fluctuate")
	public String fluctuate(Model model) {
		log.info("PriceController fluctuate start.");
		// Initialize History Search
		HistorySearch historySearch = new HistorySearch();
		historySearch.setExchange(1);
		historySearch.setStock(null);
		historySearch.setPage(1);
		historySearch.setSize(100);
		Calendar calendar = Calendar.getInstance();
		historySearch.setDate_to(calendar.getTime());
		calendar.add(Calendar.MONTH, -1);
		historySearch.setDate_from(calendar.getTime());
		// Get stock list from file
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<StockList>> typeReference = new TypeReference<List<StockList>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/hose.json");
		try {
			List<StockList> stockList = mapper.readValue(inputStream, typeReference);
			model.addAttribute("stockList", stockList);	
		} catch (IOException e) {
			e.printStackTrace();
		}	
		// Set Attributes For Model
		model.addAttribute("historySearch", historySearch);
		model.addAttribute("priceFluctuates", priceFluctuateRepository.findPriceFluctuate(historySearch.getExchange(),
				historySearch.getStock(),
				historySearch.getDate_from(),
				historySearch.getDate_to()));
		return "price/fluctuate";
	}
	
	@PostMapping("/fluctuate")
	public String fluctuateSearch(@ModelAttribute("historySearch") HistorySearch historySearch ,Model model) {
		log.info("PriceController fluctuateSearch start.");
		// Check Search All With Stock
		if (historySearch.getStock().isEmpty()) {
			historySearch.setStock(null);
		}
		// Get stock list from file
		ObjectMapper mapper = new ObjectMapper();
		String filejson = "/json/hose.json";
		if (historySearch.getExchange().equals(2)) {
			filejson = "/json/hnx.json";
		} else if(historySearch.getExchange().equals(3)) {
			filejson = "/json/upcom.json";
		}
		TypeReference<List<StockList>> typeReference = new TypeReference<List<StockList>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream(filejson);
		try {
			List<StockList> stockList = mapper.readValue(inputStream, typeReference);
			model.addAttribute("stockList", stockList);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("historySearch", historySearch);
		model.addAttribute("priceFluctuates", priceFluctuateRepository.findPriceFluctuate(historySearch.getExchange(),
				historySearch.getStock(),
				historySearch.getDate_from(),
				historySearch.getDate_to()));
		return "price/fluctuate";
	}
	
	@GetMapping("/import")
	public String importExcel(Model model) {
		log.info("PriceController importExcel start.");
		return "price/import";
	}
	
	@PostMapping("/import")
	public String doImportExcel(@RequestParam("exchange") Integer exchange, 
			@RequestParam("excelFiles") MultipartFile[] excelFiles, Model model) throws IOException, InvalidFormatException {
		log.info("PriceController doImportExcel start.");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Price> priceList = new ArrayList<Price>();
		for (MultipartFile excelFile : excelFiles) {
			priceList = new ArrayList<Price>();
			try {
				XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
				XSSFSheet worksheet = workbook.getSheetAt(0);
				for(int i=3;i<worksheet.getPhysicalNumberOfRows()+1 ;i++) {
					Price price = new Price();
					XSSFRow row = worksheet.getRow(i);
					price.setExchange(exchange);
					switch(row.getCell(0).getCellType()) {
						case NUMERIC:
							String[] resultDate = formatter.format(row.getCell(0).getDateCellValue()).split("/");
							price.setDate(formatter.parse(resultDate[1] + "/" + resultDate[0] + "/" + resultDate[2]));
							break;
						case BLANK:
						case STRING:
						case FORMULA:
							price.setDate(formatter.parse(row.getCell(0).getStringCellValue()));
							break;
						default:
							price.setDate(null);
					}
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
				workbook.close();
				priceRepository.saveAll(priceList);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("prices", priceList);
		return "price/result";
	}
}
