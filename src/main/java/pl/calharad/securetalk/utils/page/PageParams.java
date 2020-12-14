package pl.calharad.securetalk.utils.page;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.QueryParam;

@Getter
@Setter
public class PageParams {
    @QueryParam("page")
    private Integer page;
    @QueryParam("size")
    private Integer size;
}
