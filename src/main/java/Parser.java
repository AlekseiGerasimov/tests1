import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Parser {

    private String source;
    private Map<String, List<String>> map = new ConcurrentHashMap<>();
    private AtomicLong count = new AtomicLong(1);

    private static ForkJoinPool forkJoinPool = new ForkJoinPool(ProcessorUtility.getProcessorCount());


    public Parser(String source) {
        this.source = source;
    }

    public void parse() {
        try {
            forkJoinPool.submit(() -> Arrays.asList(source.split("\\r?\\n"))
                    .parallelStream()
                    .forEach(this::parse)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }
    }

    private void parse(String source) {
        StringBuilder builder = new StringBuilder();
        String[] split = source.replaceAll("\\s+", "").split("[.,*!?\\-]");
        Arrays.sort(split, String::compareTo);
        Arrays.asList(split).forEach(builder::append);
        if (split.length > 0) {
            String resultString = builder.toString();
            List<String> list = map.get(resultString);
            if (list == null) {
                list = new ArrayList<>();
                list.add(source);
                map.put(resultString, list);
            } else {
                list.add(source);
            }
        }
    }

    @Override
    public String toString() {
        return map
                .values()
                .stream()
                .map(strings -> strings
                        .stream()
                        .collect(Collectors.joining("\n", "(" + String.valueOf(count.getAndIncrement()) + ")\n", "\n")))
                .collect(Collectors.joining("\n\n"));
    }
}
