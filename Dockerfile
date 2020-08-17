FROM gradle
ADD . /Node
WORKDIR /Node
ENTRYPOINT ["gradle", "bootRun"]
