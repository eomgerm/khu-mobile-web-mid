import os
import pathlib
from datetime import datetime

import cv2
import requests

from person import Person


class SuspicionDetector:
    detected_previous = []
    HOST = "http://localhost:8000"
    email = "abc@abc.com"
    password = "password"
    token = ""
    cctv_id = 1
    THRESHOLD = 5

    def __init__(self):
        self.detected_previous = []

        res = requests.post(self.HOST + "/api/sign-in", {
            "email": self.email,
            "password": self.password,
        })
        res.raise_for_status()

        self.token = res.json()["access"]
        print(self.token)

    def add(self, detected_current, image):
        current_time = datetime.now()

        for prev_idx, person_prev in enumerate(self.detected_previous):
            if (person_prev.id not in detected_current and
                    (current_time - person_prev.detected_time).seconds >= self.THRESHOLD):
                print(f"Person(id:{person_prev.id}) has gone.")
                self.detected_previous.pop(prev_idx)

        for curr_idx, person_id in enumerate(detected_current):
            exist = False
            for prev_idx, person_prev in enumerate(self.detected_previous):
                if person_id == person_prev.id:
                    exist = True
                    print(f"Person(id:{person_id}) Detected Again")
                    if (current_time - person_prev.detected_time).seconds >= self.THRESHOLD:
                        print("\033[33mSuspicion Alert!!!\033[0m")
                        self.send(image)
                        self.detected_previous.pop(prev_idx)
                break

            if not exist:
                print(f"Person(id:{person_id}) Detected! time: {current_time}")
                person = Person(person_id, current_time)
                self.detected_previous.append(person)

        print(self.detected_previous)

    def send(self, image):
        today = datetime.now()
        save_path = pathlib.Path(os.path.join(os.getcwd(),
                                              "cctv_image",
                                              "detected",
                                              str(today.year),
                                              str(today.month),
                                              str(today.day)))

        pathlib.Path(save_path).mkdir(parents=True, exist_ok=True)

        full_path = (
                save_path
                / f"{today.hour}-{today.minute}-{today.second}-{today.microsecond}.jpg"
        )

        dst = cv2.resize(image, dsize=(960, 540), interpolation=cv2.INTER_AREA)
        cv2.imwrite(str(full_path), dst)

        headers = {"Authorization": "Bearer " + self.token, "Accept": "application/json"}

        data = {"cctv_id": self.cctv_id}
        file = {"alert_image": open(full_path, "rb")}
        res = requests.post(
            self.HOST + f"/api/cctvs/{self.cctv_id}/alerts/",
            data=data,
            files=file,
            headers=headers,
        )
        print(res.content)
