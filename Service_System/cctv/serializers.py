from rest_framework import serializers

from cctv.models import Alert, Cctv


class AlertSerializer(serializers.ModelSerializer):
    alert_image = serializers.ImageField(use_url=True)

    class Meta:
        model = Alert
        fields = "__all__"


class CctvSerializer(serializers.ModelSerializer):
    recent_image = serializers.ImageField(use_url=True)

    class Meta:
        model = Cctv
        fields = "__all__"
