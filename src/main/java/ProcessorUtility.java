import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

@Slf4j
@UtilityClass
public class ProcessorUtility {

    public static Integer getProcessorCount() {
        log.debug("Start getting number of processors");
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
        int logicalProcessorCount = centralProcessor.getLogicalProcessorCount();
        log.info("Number: " + centralProcessor);
        return logicalProcessorCount;
    }
}
