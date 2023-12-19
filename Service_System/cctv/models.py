from django.conf import settings
from django.db import models
from django.utils import timezone


# Create your models here.
class Cctv(models.Model):
    cctv_id = models.BigAutoField(primary_key=True)
    owner = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    name = models.CharField(max_length=100, blank=False, null=False)
    recent_image = models.ImageField(upload_to=f"cctv_images/", null=True)


class Alert(models.Model):
    alert_id = models.BigAutoField(primary_key=True)
    cctv_id = models.ForeignKey(Cctv, on_delete=models.CASCADE, related_name="cctv")
    created_date = models.DateTimeField(default=timezone.now())
    alert_image = models.ImageField(upload_to=f"alert_images/%Y/%m/%d")
