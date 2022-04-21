package org.example.backend.model.api.articles;

import com.example.serum.api.request.RequestQueryParams;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
public class ArticlesListQueryParams extends RequestQueryParams {

  public String tag;
  public String author;
  public String favorited;
  public Integer limit;
  public Integer offset;

}
