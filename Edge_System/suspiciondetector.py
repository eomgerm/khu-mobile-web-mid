import os
import pathlib
from datetime import datetime

import cv2
import requests


class SuspicionDetector:
    detected_previous = []
    names = []
    HOST = "https://eomgerm.pythonanywhere.com/"
    username = "eomgerm"
    password = "fb_26.55"
    token = ""
    cctv_id = 1
    targets = [0]

    def __init__(self, names):
        self.detected_previous = [None for i in range(len(names))]
        self.names = names

    def add(self, detected_current, save_dir, image):
        for i in range(len(self.detected_previous)):
            current_time = datetime.now()
            if i not in self.targets:
                continue
            if self.detected_previous[i] != None and detected_current[i] == 1:
                print(f"{self.names[i]} Detected Again")
                if (current_time - self.detected_previous[i]).seconds >= 5:
                    print("\033[33mSuspicion Alert!!!\033[0m")
                    self.send(save_dir, image)
                    self.detected_previous[i] = None
            elif self.detected_previous[i] == None and detected_current[i] == 1:
                print(f"{self.names[i]} Appeared!")
                self.detected_previous[i] = current_time
            elif self.detected_previous[i] != None and detected_current[i] == 0:
                print(f"{self.names[i]} Gone")
                self.detected_previous[i] = None

    def send(self, save_dir, image):
        today = datetime.now()
        save_path = (
            os.getcwd()
            / save_dir
            / "detected"
            / str(today.year)
            / str(today.month)
            / str(today.day)
        )
        pathlib.Path(save_path).mkdir(parents=True, exist_ok=True)

        full_path = (
            save_path
            / f"{today.hour}-{today.minute}-{today.second}-{today.microsecond}.jpg"
        )

        dst = cv2.resize(image, dsize=(960, 540), interpolation=cv2.INTER_AREA)
        cv2.imwrite(full_path, dst)

        headers = {"Authorization": "JWT " + self.token, "Accept": "application/json"}

        data = {"cctv_id": self.cctv_id}
        file = {"alert_image": open(full_path, "rb")}
        res = requests.post(
            self.HOST + f"api/cctvs/{self.cctv_id}/alerts/",
            data=data,
            files=file,
            headers=headers,
        )
        print(res.content)
