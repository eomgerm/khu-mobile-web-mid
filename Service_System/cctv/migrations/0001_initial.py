# Generated by Django 5.0 on 2023-12-12 08:51

import datetime
import django.db.models.deletion
from django.conf import settings
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name="Cctv",
            fields=[
                ("cctv_id", models.BigAutoField(primary_key=True, serialize=False)),
                ("name", models.CharField(max_length=100)),
                ("recent_image", models.ImageField(upload_to="cctv_images/")),
                (
                    "owner",
                    models.ForeignKey(
                        on_delete=django.db.models.deletion.CASCADE,
                        to=settings.AUTH_USER_MODEL,
                    ),
                ),
            ],
        ),
        migrations.CreateModel(
            name="Alert",
            fields=[
                ("alert_id", models.BigAutoField(primary_key=True, serialize=False)),
                (
                    "created_date",
                    models.DateTimeField(
                        default=datetime.datetime(
                            2023, 12, 12, 8, 51, 0, 602647, tzinfo=datetime.timezone.utc
                        )
                    ),
                ),
                ("alert_image", models.ImageField(upload_to="alert_images/%Y/%m/%d")),
                (
                    "cctv_id",
                    models.ForeignKey(
                        on_delete=django.db.models.deletion.CASCADE, to="cctv.cctv"
                    ),
                ),
            ],
        ),
    ]
