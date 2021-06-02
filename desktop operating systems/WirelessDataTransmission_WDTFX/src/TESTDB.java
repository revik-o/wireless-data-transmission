import com.WDTComponents.AppConfig;
import com.WDTComponents.DataBase.Model.FileModel;
import com.WDTComponents.DataBase.Model.TransferredFilesHistoryModel;
import com.WDTComponents.DataBase.ModelDAO.*;
import sample.Platform.PlatformDataBase;
import sample.Platform.PlatformDataBaseKt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

public class TESTDB {

    public static void main(String[] args) {
        if (!new File(PlatformDataBaseKt.getCOMMON_DIRECTORY()).exists()) new File(PlatformDataBaseKt.getCOMMON_DIRECTORY()).mkdirs();
        AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase = new PlatformDataBase();
        AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO = new DeviceModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase);
        AppConfig.DataBase.ModelDAOInterface.iFileModelDAO = new FileModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase);
        AppConfig.DataBase.ModelDAOInterface.iTrustedDeviceModelDAO = new TrustedDeviceModelModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase);
        AppConfig.DataBase.ModelDAOInterface.iAcceptedFilesHistoryModelDAO = new AcceptedFilesHistoryModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase);
        AppConfig.DataBase.ModelDAOInterface.iTransferredFilesHistoryModelDAO = new TransferredFilesHistoryModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase);
        FileModelDAO fileModelDAO = (FileModelDAO) AppConfig.DataBase.ModelDAOInterface.iFileModelDAO;
        fileModelDAO.addFile(new FileModel("kek.txt", "/oleg/path/kek1.txt", 10));
//        System.out.println(fileModelDAO.getFile(new FileModel("kek.txt", "/oleg/path/kek2.txt", 15))); // correct

//        DeviceModel deviceModel = new DeviceModel("Oleg", "Computer", "127.0.0.1");
//        AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.addNewDeviceToDatabaseWithUsingFilter("127.0.0.1", "Oleg", "Computer");
//        AppConfig.DataBase.ModelDAOInterface.iTrustedDeviceDAO.addNewTrustedDevice(new TrustedDevice(AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.getDeviceId(new DeviceModel("Oleg", "Computer", "127.0.0.1")), "good friend"));
//        AppConfig.DataBase.ModelDAOInterface.iTrustedDeviceDAO.updateInfoAboutTrustedDevice(new TrustedDevice(3, AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.getDeviceId(new DeviceModel("Oleg", "Computer", "127.0.0.1")), "good321321 friend"));
//        AppConfig.DataBase.ModelDAOInterface.iTrustedDeviceDAO.removeTrustedDevice(new TrustedDevice(3));
//        ((IDAO) AppConfig.DataBase.ModelDAOInterface.iTrustedDeviceDAO).selectAll().forEach(strings -> System.out.println(Arrays.toString(strings))); // correct

//        AppConfig.DataBase.ModelDAOInterface.iAcceptedFilesHistoryModelDAO.addNewAcceptedFileOrUpdate(new AcceptedFilesHistoryModel(
//                AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.getDeviceId(deviceModel),
//                fileModelDAO.getFileId(new FileModel("kek.txt", "/oleg/path/kek1.txt", 10)),
//                LocalDate.now().toString()
//        ));
//        ((IDAO) AppConfig.DataBase.ModelDAOInterface.iAcceptedFilesHistoryModelDAO).selectAll().forEach(strings -> System.out.println(Arrays.toString(strings))); // correct

//        AppConfig.DataBase.ModelDAOInterface.iTransferredFilesHistoryModelDAO.addNewTransferredFileOrUpdate(
//                new TransferredFilesHistoryModel(
//                        fileModelDAO.getFileId(new FileModel("kek.txt", "/oleg/path/kek1.txt", 10)),
//                        LocalDate.now().toString()
//                )
//        );
//        ((IDAO) AppConfig.DataBase.ModelDAOInterface.iTransferredFilesHistoryModelDAO).selectAll().forEach(strings -> System.out.println(Arrays.toString(strings))); // correct
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

}