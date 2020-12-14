package pl.calharad.securetalk.utils.page;

import lombok.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public <U> Page<U> map(Function<T, U> mapper) {
        List<U> mappedList = content.stream().map(mapper).collect(Collectors.toList());
        return Page.<U>builder()
                .page(page)
                .content(mappedList)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}
