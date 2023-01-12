main=payment-practice

.PHONY: build-image
build-image:
	./mvnw package && \
	docker build -t payment-practice .

.PHONY: clean
clean:
	docker-compose down && \
	rm -rf target