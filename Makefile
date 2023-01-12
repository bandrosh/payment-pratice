main=payment-practice

.PHONY: build-image
build-image:
	cd database && \
	docker-compose up -d && \
	cd .. && \
	./mvnw package && \
	cd database && \
	docker-compose down && \
	cd .. && \
	docker build -t payment-practice .

.PHONY: clean
clean:
	docker-compose down && \
	docker system prune -a && \
	rm -rf target