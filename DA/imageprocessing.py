from flask import Flask, request, jsonify
from sklearn.preprocessing import LabelEncoder
from tensorflow import keras
from tensorflow.keras import layers
from PIL import Image
import csv
import pandas as pd
import numpy as np
import os

app = Flask(__name__)

# 이미지를 객관적으로 변환하는 함수
def load_image(img_path, target_size):
    img = Image.open(img_path)
    img = img.resize(target_size)
    img = np.array(img) / 255.0
    return img

# 모델을 로드하고 반환하는 함수
def load_model():
    # 데이터 가져오기
    labels = pd.read_csv("./DA/data/labels.csv")

    # 이미지 파일 데이터 라벨에 집어넣기
    filePath = './DA/data/train/'
    f = lambda x: filePath + x + '.jpg'

    labels['imgpath'] = labels['id'].apply(f)

    # 데이터 프레임의 이미지 파일 경로 사용하여 이미지 로드하고 객체 변환
    target_size = (100, 100)
    labels['image_path'] = labels['imgpath']
    labels['image'] = labels['image_path'].apply(lambda x: load_image(x, target_size))

    # 데이터 전처리
    X = np.array([img for img in labels['image']])
    X = X / 255.0

    # 라벨 인코딩
    label_encoder = LabelEncoder()
    y = label_encoder.fit_transform(labels['breed'])

    # 클래스 개수 확인
    num_classes = len(label_encoder.classes_)

    image_height = 100  # 이미지 높이
    image_width = 100  # 이미지 너비
    channels = 3  # 이미지 채널 수 (RGB 이미지의 경우 3)

    # CNN 모델 구성
    model = keras.Sequential([
        layers.Conv2D(32, kernel_size=(3, 3), activation='relu', input_shape=(image_height, image_width, channels)),
        layers.MaxPooling2D(pool_size=(2, 2)),
        layers.Conv2D(64, kernel_size=(3, 3), activation='relu'),
        layers.MaxPooling2D(pool_size=(2, 2)),
        layers.Flatten(),
        layers.Dense(128, activation='relu'),
        layers.Dense(num_classes, activation='softmax')
    ])

    # 모델 컴파일
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

    # 모델 학습
    model.fit(X, y, batch_size=32, epochs=10, validation_split=0.2)

    return model, label_encoder

@app.route('/predict', methods=['POST'])
def predict():
    # 전송된 이미지 받기
    image_file = request.files['image']
    image = Image.open(image_file)

    # 이미지 전처리
    target_size = (100, 100)
    preprocessed_image = load_image(image, target_size)

    # 모델 로드 및 예측
    model, label_encoder = load_model()
    predictions = model.predict(np.expand_dims(preprocessed_image, axis=0))
    predicted_class = np.argmax(predictions)
    predicted_label = label_encoder.inverse_transform([predicted_class])[0]

    response = {
        'predicted_label': predicted_label,
    }

    # 실제 품종과 일치 여부 확인
    true_breed = request.form['breed']
    if true_breed == predicted_label:
        response['match'] = True
    else:
        response['match'] = False

    return jsonify(response)

if __name__ == '__main__':
    app.run()
