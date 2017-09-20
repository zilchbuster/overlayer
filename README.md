# Image overlay API built with spring, Catalano

This is an api that overlays 2 images. It's composed of 2 endpoints.

`/overlay/overlay`
The first takes in 3 form parameters: 2 files and an opacity level. It generates a token.

```bash
curl -X POST localhost:8080/overlay/overlay -F imageBlob1=@fjords.jpg -F imageBlob2=@image-slider2.jpg -F opacity="0.2"
```
```json
{"status":"success","token":"77fbe3e1-0839-48bd-8c38-163f68df6699"}
```

`/overlay/image`
The second is a GET, it takes the token as a query parameter and returns the image as response body

```bash
curl -X GET localhost:8080/overlay/image?token=77fbe3e1-0839-48bd-8c38-163f68df6699 > output.jpg
```