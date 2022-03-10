package com.atividade.helper;

import com.atividade.exception.ResourceNotFoundException;
import com.atividade.model.Cliente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Nome", "DataDeNascimento", "Rg", "Cpf","Telefone" };
    static String SHEET = "Planilha1";
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<Cliente> excelToTutorials(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

                    sheet.getLastRowNum();
            List<Cliente> clientes = new ArrayList<Cliente>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Cliente cliente = new Cliente();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            currentCell.setCellType(CellType.STRING);
                            cliente.setNome(currentCell.getStringCellValue());
                            break;
                        case 1:
                            cliente.setDataDeNascimento(currentCell.getLocalDateTimeCellValue() != null ? currentCell.getLocalDateTimeCellValue().toLocalDate():null);
                            break;
                        case 2:
                            currentCell.setCellType(CellType.STRING);
                            cliente.setRg(currentCell.getStringCellValue());
                            break;
                        case 3:
                            currentCell.setCellType(CellType.STRING);
                            cliente.setCpf(currentCell.getStringCellValue());
                            break;
                        case 4:
                            currentCell.setCellType(CellType.STRING);
                            cliente.setTelefone(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                if (cliente.getNome()!=""&&cliente.getTelefone()!=null&&cliente.getCpf()!=null&&cliente.getRg()!=""&&cliente.getDataDeNascimento()!=null){
                    clientes.add(cliente);
                }
                else if(cliente.getNome()==""&&cliente.getTelefone()==null&&cliente.getCpf()==null&&cliente.getRg()==null&&cliente.getDataDeNascimento()==null){

                }
                else{
                    var linha = currentRow.getRowNum()+1;
                    throw new ResourceNotFoundException("Paramos na Linha "+linha+ " por falta de dados do cliente especificado nesta linha, confira os dados e tente novamente");
                }

            }
            workbook.close();
            return clientes;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao analizar o arquivo Excel: " + e.getMessage());
        }
    }

}
