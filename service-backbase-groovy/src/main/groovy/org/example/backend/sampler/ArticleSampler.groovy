package org.example.backend.sampler

import org.example.backend.model.request.ArticleCreateReqBody
import org.example.backend.model.request.ArticleCreateReqBody.Article

import static org.example.backend.extensions.DataGenerator.generateUuidString

class ArticleSampler {

	static ArticleCreateReqBody minimalInput() {
		new ArticleCreateReqBody().tap {
			article = new Article().tap {
				title = "Title ${generateUuidString()}"
				description = "Description ${generateUuidString()}"
				body = "Body ${generateUuidString()}"
			}
		}
	}

	static ArticleCreateReqBody fullInput() {
		minimalInput().tap {
			article.tagList = ([generateUuidString(), generateUuidString()])
		}
	}
}
