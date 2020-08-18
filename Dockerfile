FROM gradle
COPY . /Node
WORKDIR /Node
ENTRYPOINT ["gradle", "bootRun"]
