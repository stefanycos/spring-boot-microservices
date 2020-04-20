package br.com.springboot.demo.product.service.service;

import java.io.InputStream;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

import br.com.springboot.demo.product.service.domain.ImageStatus;
import br.com.springboot.demo.product.service.domain.Product;
import br.com.springboot.demo.product.service.domain.ProductImage;
import br.com.springboot.demo.product.service.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class FileServiceFallback {

	private final GridFsTemplate gridFsTemplate;

	private final GridFsOperations operations;

	public void store(Product product) {
		final ProductImage image = product.getImage();

		final DBObject metadata = new BasicDBObject();
		metadata.put("uploaded", false);

		final InputStream inputStream = FileUtils.toInputStream(image.getBase64());
		final String id = gridFsTemplate.store(inputStream, image.getFilename(), metadata).toString();

		image.setId(id);
		image.setStatus(ImageStatus.WAITING_UPLOAD);
	}

	public void restore(Product product) {
		final ProductImage image = product.getImage();

		final GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(image.getId())));

		try {

			final InputStream input = operations.getResource(file).getInputStream();
			final String base64 = FileUtils.toBase64(input);
			image.setBase64(base64);
			image.setStatus(ImageStatus.UPLOADED);

		} catch (final Exception e) {
			log.error("Error when recovering image from database. Error '{}'", e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}
