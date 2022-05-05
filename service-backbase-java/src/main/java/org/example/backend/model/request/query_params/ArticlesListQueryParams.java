package org.example.backend.model.request.query_params;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.net.testit.serum.api.request.RequestQueryParams;

@Accessors(chain = true)
@Setter
public class ArticlesListQueryParams extends RequestQueryParams {

  public String tag;
  public String author;
  public String favorited;
  public Integer limit;
  public Integer offset;

}
